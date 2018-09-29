package plotter;

import elements.Parameter;
import javafx.scene.image.Image;
import util.Adjustable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Plotter implements Adjustable {

	protected HashMap<String, Parameter> parameters = new HashMap<>();

	Plotter(int w, int h) {
	}

	protected double p(String name) {
		return parameters.get(name).getValue();
	}

	@Override
	public List<Parameter> getParameters() {
		return new ArrayList<>(parameters.values());
	}

	// Registers the specified n-dimensional point in the plot
	public abstract void plot(double[] point);

	// Produces an image representing the plot
	public abstract Image getOutput();

	public abstract void reset();
}
