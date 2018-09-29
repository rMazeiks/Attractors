package plotter;

import elements.Parameter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import util.CommonJava;

/**
 * Plots three dimensions:
 * [0] - the y axis
 * [1] - the x axis
 * [2] - changes the hue of the color plotted
 */
public class Color2DPlotter extends Plotter {
	private long[][][] data;


	public Color2DPlotter(int w, int h) {
		super(w, h);
		data = new long[h][w][3];
		parameters.put("zoom", new Parameter(0.125, "Zoom", 0.1, 1));
		parameters.put("sat", new Parameter(0.8, "Saturation", 0, 1));
		parameters.put("col", new Parameter(360, "Color Scale", 90, 720));
		parameters.put("off", new Parameter(0, "Color Offset", 0, 1));
		Parameter boost = new Parameter(50, "Brightness Boost", 1, 200);
		boost.setRequiresRefresh(false);
		parameters.put("boost", boost);
		parameters.put("x", new Parameter(0, "Translate X", -w / 2, w / 2));
		parameters.put("y", new Parameter(0, "Translate Y", -h / 2, h / 2));
	}

	@Override
	public Plotter copyOfSize(int w, int h) {
		Color2DPlotter plotter = new Color2DPlotter(w, h);
		plotter.parameters = CommonJava.clone(this.parameters);
		return plotter;
	}

	@Override
	public void plot(double[] point) {
		int min = Math.min(data.length, data[0].length);

		int x = (int) ((point[1] * p("zoom")) * min + data[0].length / 2 + p("x"));
		int y = (int) ((point[0] * p("zoom")) * min + data.length / 2 + p("y"));

		if (x < 0 || y < 0 || x >= data[0].length || y >= data.length) return;

		Color hsb = Color.hsb(colorize(point[2]), p("sat"), 1);

		double f = 100;
		data[y][x][0] += hsb.getRed() * f;
		data[y][x][1] += hsb.getGreen() * f;
		data[y][x][2] += hsb.getBlue() * f;

	}

	private double colorize(double v) {
		double a = (v + p("off")) % 1;
		if (a < 0) a += 1;
		return a * p("col");
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
		return 1 - (Math.pow(1 - v, p("boost")));
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
