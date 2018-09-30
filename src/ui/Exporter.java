package ui;

import elements.PositiveIntegerInput;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import plotter.Iterator;
import plotter.Plotter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * A pop-up window that the user can use to render and export the Attractor.
 */
public class Exporter extends Stage {
	private Main main;
	private IntegerProperty width = new SimpleIntegerProperty(1200);
	private IntegerProperty height = new SimpleIntegerProperty(900);
	private IntegerProperty iterationProperty = new SimpleIntegerProperty(420000000);
	private DoubleProperty progressProperty = new SimpleDoubleProperty(0);
	private boolean windowCreated = false;

	public Exporter(Main main) {
		this.main = main;
	}

	private void createWindow() {
		VBox exporter = new VBox();
		VBox inputs = new VBox();
		{
			Label wLabel = new Label("Width:");
			TextField w = PositiveIntegerInput.makeTextField(width);

			Label hLabel = new Label("Height:");
			TextField h = PositiveIntegerInput.makeTextField(height);


			Label iLabel = new Label("Iterations:");
			TextField iterations = PositiveIntegerInput.makeTextField(iterationProperty);

			inputs.getChildren().addAll(wLabel, w, hLabel, h, iLabel, iterations);
			inputs.setSpacing(10);
		}

		Button start = new Button("Export...");
		start.setOnAction(event -> export());

		ProgressBar bar = new ProgressBar();
		bar.progressProperty().bind(progressProperty);

		exporter.getChildren().addAll(inputs, start, bar);
		exporter.setSpacing(20);
		exporter.setPadding(new Insets(40));

		this.setScene(new Scene(exporter));
	}

	public void open() {
		if (!windowCreated) createWindow();
		windowCreated = true;

		this.show();
	}

	private void export() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("PNG image", "png"));
		File file = fileChooser.showSaveDialog(this);

		if (file == null) return;

		Plotter finalPlotter = main.getPlotter().get().copyOfSize(width.get(), height.get());
		Iterator finalRender = new Iterator(main.getTransformations(), finalPlotter, iterationProperty.get(), 500);

		progressProperty.bind(finalRender.progressProperty());

		finalRender.setOnSucceeded(event -> {

			try {
				Image image = finalRender.get();
				ImageIO.write(SwingFXUtils.fromFXImage(image,
						null), "png", file);
			} catch (InterruptedException | IOException | ExecutionException e) {
				e.printStackTrace();
			}
		});

		finalRender.threadRun();

	}
}
