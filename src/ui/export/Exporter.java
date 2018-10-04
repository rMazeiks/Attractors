package ui.export;

import elements.PositiveIntegerInput;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.Main;

/**
 * A pop-up window that the user can use to render and export the Attractor.
 */
public class Exporter extends Stage {
	private Main main;
	private IntegerProperty imgWidth = new SimpleIntegerProperty(1200);
	private IntegerProperty imgHeight = new SimpleIntegerProperty(900);
	private IntegerProperty iterationProperty = new SimpleIntegerProperty(420000000);
	private DoubleProperty progress = new SimpleDoubleProperty(0);
	private boolean windowCreated = false;

	public Exporter(Main main) {
		this.main = main;
	}

	public Main getMain() {
		return main;
	}

	public int getImgWidth() {
		return imgWidth.get();
	}

	public IntegerProperty imgWidthProperty() {
		return imgWidth;
	}

	public int getImgHeight() {
		return imgHeight.get();
	}

	public IntegerProperty imgHeightProperty() {
		return imgHeight;
	}

	public int getIterationProperty() {
		return iterationProperty.get();
	}

	public IntegerProperty iterationPropertyProperty() {
		return iterationProperty;
	}

	public double getProgress() {
		return progress.get();
	}

	public DoubleProperty progressProperty() {
		return progress;
	}

	private void createWindow() {
		VBox exporter = new VBox();
		VBox inputs = new VBox();
		{
			Label wLabel = new Label("Width:");
			TextField w = PositiveIntegerInput.makeTextField(imgWidth);

			Label hLabel = new Label("Height:");
			TextField h = PositiveIntegerInput.makeTextField(imgHeight);


			Label iLabel = new Label("Iterations:");
			TextField iterations = PositiveIntegerInput.makeTextField(iterationProperty);

			inputs.getChildren().addAll(wLabel, w, hLabel, h, iLabel, iterations);
			inputs.setSpacing(10);
		}

		TabPane options = new TabPane();
		{
			options.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
			options.setStyle("-fx-border-color: #0002"); // outline

			Tab image = new Tab("Image", new ImageExp(this));

			Tab animation = new Tab("Animation", new AnimExp(this));

			options.getTabs().addAll(image, animation);
		}

		ProgressBar bar = new ProgressBar();
		bar.progressProperty().bind(progress);

		exporter.getChildren().addAll(inputs, options, bar);
		exporter.setSpacing(20);
		exporter.setPadding(new Insets(40));

		this.setScene(new Scene(exporter));
	}

	public void open() {
		if (!windowCreated) createWindow();
		windowCreated = true;

		this.show();
		this.requestFocus();
	}


}
