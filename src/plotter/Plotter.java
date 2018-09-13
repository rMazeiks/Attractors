package plotter;

import javafx.scene.image.Image;

public abstract class Plotter {

	Plotter(int w, int h)  {}

	// Registers the specified n-dimensional point in the plot
	public abstract void plot(double[] point);

	// Produces an image representing the plot
	public abstract Image getOutput();

	public abstract void reset();
}
