package ui;

import elements.LabeledSlider;
import elements.Parameter;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plotter.Color3DPlotter;
import plotter.Iterator;
import plotter.PlotterProperty;
import transformation.Transformation;
import ui.sections.Control;

/**
 * A class that puts everything together, and contains references to all the data in the "project"
 */
public class Main extends Application {
	static int RESET_INTERVAL = 100;

	private BorderPane outputSection;
	private VBox plotterSliders;
	private PlotterProperty plotter = new PlotterProperty();
	private ObservableList<Transformation> transformations = FXCollections.observableArrayList();
	private ImageView outputView;
	private Iterator render;
	private DoubleProperty renderProgress = new SimpleDoubleProperty(0);
	private Iterator preview;
	private boolean needsPreview = false;
	private StackPane renderContainer = new StackPane();


	public Main() {
		transformations.addListener((ListChangeListener<Transformation>) c -> refresh());
	}

	public ObservableList<Transformation> getTransformations() {
		return transformations;
	}

	public double getRenderProgress() {
		return renderProgress.get();
	}

	public DoubleProperty renderProgressProperty() {
		return renderProgress;
	}

	public PlotterProperty getPlotter() {
		return plotter;
	}

	public void setPlotter(PlotterProperty p) {
		this.plotter = p;
	}

	@Override
	public void start(Stage primaryStage) {

		outputView = new ImageView();

		BorderPane all = new BorderPane();
		all.setLeft(controlTabs());

		outputSection = new BorderPane();
		ProgressBar renderBar = new ProgressBar();
		renderBar.progressProperty().bind(renderProgressProperty());
		outputSection.setBottom(renderBar);
		all.setCenter(outputSection);

		primaryStage.setScene(new Scene(all));
		primaryStage.show();
		primaryStage.setMaximized(true);

		outputSection.setCenter(renderContainer);
		renderContainer.getChildren().add(outputView);

		makePlotter(900, 600);

		refresh();
	}

	private void makePlotter(int w, int h) {
		plotter.set(new Color3DPlotter(w, h));

		plotterSliders.getChildren().removeIf(node -> true); // remove all
		for (Parameter param : getPlotter().get().getParameters()) {
			LabeledSlider slider = new LabeledSlider(param);
			plotterSliders.getChildren().add(slider);

			param.addListener((observable, oldValue, newValue) -> {
						if (param.doesRequireRefresh()) {
							refresh();
						} else {
							update();
						}
					}
			);
		}
	}

	/**
	 * Asynchronously finalizes the output of the plotter and shows it
	 */
	private void update() {
		if (needsPreview) return; // gonna happen soon anyway

		Task<Image> task = new Task<Image>() {
			@Override
			protected Image call() {
				return plotter.get().getOutput();
			}
		};
		task.setOnSucceeded(event -> showImg(task.getValue()));

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();

	}

	private Node controlTabs() {
		TabPane controlTabs = new TabPane();
		controlTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		Tab transformationCtrls = new Tab();
		transformationCtrls.setText("Transformations");
		transformationCtrls.setContent(new ScrollPane(new Control(this)));

		Tab plotterCtrls = new Tab();
		plotterCtrls.setText("Plotter");
		plotterSliders = new VBox();
		plotterSliders.setSpacing(10);
		plotterSliders.setPadding(new Insets(20));
		plotterCtrls.setContent(plotterSliders);

		controlTabs.getTabs().addAll(transformationCtrls, plotterCtrls);

		return controlTabs;
	}

	/**
	 * Should be called whenever the attractor's preview rendering needs to be repeated.
	 * This will not necessarily immediately start the preview render. If another preview render is in progress, it will be finished to reflect the previous changes
	 *
	 * When the preview rendering is done, if there is no need for a repeat preview rendering, the improved render is started
	 */
	public void refresh() {
		if (render != null) render.cancel();

		if (preview != null) {
			needsPreview = true;

		} else {
			preview = new Iterator(transformations, plotter.get(), 100000, RESET_INTERVAL);
			needsPreview = false;

			preview.setOnSucceeded(event -> {
				showImg(preview.getValue());
				preview = null;

				if (needsPreview) {
					refresh(); // generate the next preview
				} else {
					render(1);
				}
			});

			preview.threadRun();
		}


	}

	private void render(int iteration) {
		render = new Iterator(transformations, plotter.get(), (long) (100000 * Math.pow(1.5, iteration)), RESET_INTERVAL, false);

		render.setOnSucceeded(event1 -> {
			showImg(render.getValue());
			render(iteration + 1);
		});
		renderProgress.bind(render.progressProperty());

		render.threadRun();
	}

	private void showImg(Image value) {
		outputView.setImage(value);
	}

	public void addTransformation(Transformation newOne) {
		transformations.add(newOne);
		for (Parameter p : newOne.getParameters()) {
			p.addListener(observable -> refresh());
		}
	}

	public void stopRender() {
		render.cancel();
	}
}
