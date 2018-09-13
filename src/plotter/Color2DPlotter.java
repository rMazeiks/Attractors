package plotter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Plots three dimensions:
 * [0] - the y axis
 * [1] - the x axis
 * [2] - changes the hue of the color plotted
 */
public class Color2DPlotter extends Plotter {
	private long[][][] data;
	private double zoom = 0.125;


	public Color2DPlotter(int w, int h) {
		super(w, h);
		data = new long[h][w][3];
	}

	@Override
	public void plot(double[] point) {
		int min = Math.min(data.length, data[0].length);
		if(Double.isNaN(point[0])) System.out.println("sdfsdf");

		int x = (int) ((point[1] * zoom) * min + data[0].length / 2);
		int y = (int) ((point[0] * zoom) * min + data.length / 2);

		if (x < 0 || y < 0 || x >= data[0].length || y >= data.length) return;

		Color hsb = Color.hsb(colorize(point[2]), 0.8, 1);

		double f = 100;
		data[y][x][0] += hsb.getRed() * f;
		data[y][x][1] += hsb.getGreen() * f;
		data[y][x][2] += hsb.getBlue() * f;

	}

	private double colorize(double v) {
		double a = (v % 1);
		if (a < 0) a += 1;
		return a*360;
	}

	@Override
	public Image getOutput() {
		long max = 0;
		for (int y = 0; y < data.length; y++) {
			for (int x = 0; x < data[0].length; x++) {
				for (int i = 0; i < 3; i++) {
					if (max < data[y][x][i]) max = data[y][x][i];
				}
			}
		}

		WritableImage image = new WritableImage(data[0].length, data.length);

		PixelWriter writer = image.getPixelWriter();
		for (int y = 0; y < image.getHeight(); y++) { // todo optimize in one go
			for (int x = 0; x < image.getWidth(); x++) {
				writer.setColor(x, y, Color.color(
						bright((double) data[y][x][0] / max),
						bright((double) data[y][x][1] / max),
						bright((double) data[y][x][2] / max)));
			}
		}
		return image;
	}

	private double bright(double v) {
		return 1-(Math.pow(1-v, 50));
	}

	@Override
	public void reset() {
		for (int y = 0; y < data.length; y++) {
			for (int x = 0; x < data[0].length; x++) {
				for (int i = 0; i < 3; i++) {
					data[y][x][i] = 0;
				}
			}
		}
	}
}
