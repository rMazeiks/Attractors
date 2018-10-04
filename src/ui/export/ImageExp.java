package ui.export;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import plotter.Iterator;
import plotter.Plotter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ImageExp extends VBox {
	Exporter exporter;

	public ImageExp(Exporter exporter) {
		this.exporter = exporter;
		Button start = new Button("Export...");
		start.setOnAction(event -> export());

		getChildren().add(start);
	}

	private void export() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("PNG image", "png"));
		File file = fileChooser.showSaveDialog(exporter);

		if (file == null) return;

		Plotter finalPlotter = exporter.getMain().getPlotter().get()
				.copyOfSize(exporter.getImgWidth(), exporter.getImgHeight());
		Iterator finalRender = new Iterator(
				exporter.getMain().getTransformations(),
				finalPlotter,
				exporter.getIterationProperty(),
				500);

		exporter.progressProperty().bind(finalRender.progressProperty());

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
