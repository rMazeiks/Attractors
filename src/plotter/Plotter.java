package plotter;

import elements.Parameter;
import javafx.scene.image.Image;
import util.Adjustable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Plotter is a class that is able to plot n-dimensional points and produce an output in the form of an ImageExp.
 */
public abstract class Plotter implements Adjustable {

	protected HashMap<String, Parameter> parameters = new HashMap<>();

	/**
	 * Initializes the plotter
	 *
	 * @param w the width of the image the Plotter will produce
	 * @param h the height of the image the Plotter will produce
	 */
	Plotter(int w, int h) {
	}

	/**
	 * Returns a Plotter with the specified width and height that is otherwise a perfect deep copy of the current Plotter.
	 *
	 * @param w the width of the image the Plotter will produce
	 * @param h the height of the image the Plotter will produce
	 * @return a similar Plotter with a different size
	 */
	public abstract Plotter copyOfSize(int w, int h);

	/**
	 * A shorthand method for retrieving the value of a parameter
	 *
	 * @param name the name of the parameter to retrieve
	 * @return the value of the specified parameter
	 */
	protected double p(String name) {
		return parameters.get(name).getValue();
	}

	/**
	 *
	 * @return a list of <code>Parameter</code>s this plotter has. Changing the values of the <code>Parameter</code>s may affect the behavior of the Plotter
	 */
	@Override
	public List<Parameter> getParameters() {
		return new ArrayList<>(parameters.values());
	}

	/**
	 * Plots (registers) the specified n-dimensional point in the plot
	 */
	public abstract void plot(double[] point);

	/**
	 * Produces an image representing the plot
	 */
	public abstract Image getOutput();

	/**
	 * Clears all records of the plotted points. Does not affect the Plotter's parameters
	 */
	public abstract void reset();
}
