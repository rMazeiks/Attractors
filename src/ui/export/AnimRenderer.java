package ui.export;

import elements.Parameter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import plotter.Iterator;
import plotter.Plotter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AnimRenderer {
	Exporter exporter;
	DoubleProperty progress;
	Parameter automated;
	private File file;
	private double start;
	private double end;
	private double incr;
	private boolean renderStarted = false;


	public AnimRenderer(Exporter exporter, Parameter automated) {
		this.exporter = exporter;
		this.automated = automated;
	}

	public DoubleProperty render(File file, double start, double end, double incr) {
		if (renderStarted) throw new RuntimeException("Only run AnimRender once");
		renderStarted = true;

		this.file = file;
		this.start = start;
		this.end = end;
		this.incr = incr;

		progress = new SimpleDoubleProperty(0);

		renderFrame(0);

		return progress;
	}

	private void renderFrame(int i) {
		double current = start + incr * i;
		if (current > end) {
			progress.set(1);
			return;
		}
		progress.set((current - start) / (end / start));
		automated.set(current);


		Plotter finalPlotter = exporter.getMain().getPlotter().get()
				.copyOfSize(exporter.getImgWidth(), exporter.getImgHeight());
		Iterator finalRender = new Iterator(
				exporter.getMain().getTransformations(),
				finalPlotter,
				exporter.getIterationProperty(),
				500);

		finalRender.setOnSucceeded(event -> {

			try {
				Image image = finalRender.get();
				ImageIO.write(SwingFXUtils.fromFXImage(image,
						null), "png", new File(
						file.getAbsolutePath() + "/" + i + ".png"));
				renderFrame(i + 1);
			} catch (InterruptedException | IOException | ExecutionException e) {
				e.printStackTrace();
			}
		});

		finalRender.threadRun();

	}

}
