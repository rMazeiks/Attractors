package ui;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import plotter.Color2DPlotter;
import plotter.Iterator;
import plotter.Plotter;
import transformation.FourDimWeird2;
import transformation.Transformation;
import ui.sections.Control;

public class Main extends Application {
	private Plotter plotter;
	private ObservableList<Transformation> transformations = FXCollections.observableArrayList();
	private ImageView output;
	private Iterator render;
	private DoubleProperty renderProgress = new SimpleDoubleProperty(0);
	private Iterator preview;
	private boolean needsPreview = false;

	public Main() {
		transformations.addListener((ListChangeListener<Transformation>) c -> update());
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

	public Plotter getPlotter() {
		return plotter;
	}

	public void setPlotter(Plotter p) {
		this.plotter = p;
	}

	@Override
	public void start(Stage primaryStage) {
		plotter = new Color2DPlotter(800, 600);
		transformations.add(new FourDimWeird2());

		output = new ImageView();

		BorderPane app = new BorderPane();
		app.setLeft(new ScrollPane(new Control(this)));

		BorderPane outputSection = new BorderPane();
		ProgressBar renderBar = new ProgressBar();
		renderBar.progressProperty().bind(renderProgressProperty());
		outputSection.setBottom(renderBar);
		outputSection.setCenter(output);
		app.setCenter(outputSection);

		primaryStage.setScene(new Scene(app));
		primaryStage.show();
		primaryStage.setMaximized(true);

		update();
	}

	public void update() {
		if (render != null) render.cancel();

		if (preview != null) {
			needsPreview = true;
		} else {

			preview = new Iterator(transformations, plotter, 100000, 500);
			needsPreview = false;

			preview.setOnSucceeded(event -> {
				showImg(preview.getValue());

				preview = null;

				if (needsPreview) {
					update(); // generate the next preview
				} else {

					render = new Iterator(transformations, plotter, 10000000, 500);

					render.setOnSucceeded(event1 -> {
						showImg(render.getValue());
					});
					renderProgress.bind(render.progressProperty());

					render.threadRun();

				}
			});

			preview.threadRun();
		}


	}

	private void showImg(Image value) {
		output.setImage(value);
	}
}
