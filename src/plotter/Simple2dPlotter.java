package plotter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static java.lang.Math.*;

public class Simple2dPlotter extends Plotter {
	private int[][] count;
	private double zoom = 0.25;


	public Simple2dPlotter(int w, int h) {
		super(w, h);
		count = new int[h][w];
	}

	public void plot(double[] point) {
		int x = (int) ((point[1]  * zoom / 2 + 0.5) * count[0].length);
		int y = (int) ((point[0]  * zoom / 2 + 0.5) * count.length);
		if (x < 0 || y < 0 || x >= count[0].length || y >= count.length) return;
		count[y][x]++;
	}

	@Override
	public Image getOutput() {
		int max = 0;
		for (int y = 0; y < count.length; y++) {
			for (int x = 0; x < count[0].length; x++) {
				if(max<count[y][x]) max = count[y][x];
			}
		}
		WritableImage image = new WritableImage(count[0].length, count.length);
		PixelWriter pw = image.getPixelWriter();
		for (int y = 0; y < count.length; y++) {
			for (int x = 0; x < count[0].length; x++) {
				double b = min(1, (double)count[y][x]/max);
				b = 1-pow(1-b, 30);
				pw.setColor(x, y, new Color(b, b, b, 1));
			}
		}
		return image;
	}

	@Override
	public void reset() {
		for (int y = 0; y < count.length; y++) {
			for (int x = 0; x < count[0].length; x++) {
				count[y][x] = 0;
			}
		}
	}
}
