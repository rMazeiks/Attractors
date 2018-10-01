package plotter;

import elements.Parameter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import util.CommonJava;

import static java.lang.Math.*;

/**
 * A Plotter that plots four dimensions:
 * [0] - the y axis
 * [1] - the x axis
 * [2] - the z axis
 * [3] - changes the hue of the color plotted
 * <p>
 * This plotter has a few parameters that affect how the points are rendered.
 */
public class Color3DPlotter extends Plotter {
	private long[][][] data;


	public Color3DPlotter(int w, int h) {
		super(w, h);
		data = new long[h][w][3];
		parameters.put("roty", new Parameter(0, "Rotate Y", -4, 4));
		parameters.put("rotx", new Parameter(0, "Rotate X", -4, 4));
		parameters.put("depth", new Parameter(5, "Depth", 3, 20));

		parameters.put("zoom", new Parameter(1, "Zoom", 0.1, 10));
		parameters.put("sat", new Parameter(0.8, "Saturation", 0, 1));
		parameters.put("col", new Parameter(360, "Color Scale", 90, 720));
		parameters.put("off", new Parameter(0, "Color Offset", 0, 1));
		Parameter boost = new Parameter(50, "Brightness Boost", 1, 200);
		boost.setRequiresRefresh(false);
		parameters.put("boost", boost);

	}

	@Override
	public Plotter copyOfSize(int w, int h) {
		Color3DPlotter plotter = new Color3DPlotter(w, h);
		plotter.parameters = CommonJava.clone(this.parameters);
		return plotter;
	}

	@Override
	public void plot(double[] point) {
		int min = Math.min(data.length, data[0].length);

		double z = point[2];
		double x = point[1];
		double y = point[0];

		// first, rotate around the Y (horizontal) axis

		double z2 = sin(p("roty")) * x + cos(p("roty")) * z;
		double y2 = y;
		double x2 = -sin(p("roty")) * z + cos(p("roty")) * x;

		// Then, rotate around x
		double z3 = sin(p("rotx")) * y2 + cos(p("rotx")) * z2;
		double y3 = -sin(p("rotx")) * z2 + cos(p("rotx")) * y2;
		double x3 = x2;

		double depth = (z3 + p("depth"));
		if (depth < 0) return;// behind camera
		double XX = atan(x3 / depth);
		double YY = atan(y3 / depth);

		int column = (int) ((XX * p("zoom")) * min + data[0].length / 2);
		int row = (int) ((YY * p("zoom")) * min + data.length / 2);

		if (column < 0 || row < 0 || column >= data[0].length || row >= data.length) return; // outside of view

		Color hsb = Color.hsb(colorize(point[3]), p("sat"), 1);

		double brightness = 100 * (atan(1 / depth) / PI * 2); // points further away are "smaller", less bright
		data[row][column][0] += hsb.getRed() * brightness;
		data[row][column][1] += hsb.getGreen() * brightness;
		data[row][column][2] += hsb.getBlue() * brightness;

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
