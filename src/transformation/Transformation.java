package transformation;

import elements.Parameter;
import util.Adjustable;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.random;

/**
 * A Transformation can transform an n-dimensional point (represented by a double array) into another one. It can have parameters that affect its behavior.
 */
public abstract class Transformation implements Adjustable {
	protected List<Parameter> parameters;

	protected Transformation() {
		parameters = new ArrayList<>();
	}

	/**
	 * The name of the transformation (to be displayed to the user)
	 *
	 * @return
	 */
	public abstract String transformationName();

	/**
	 * A convenience shorthand function to retrieve the parameter value with the specified index.
	 *
	 * @param index
	 * @return
	 */
	protected double p(int index) {
		return parameters.get(index).get();
	}

	/**
	 * Adds the specified number of parameters to the Parameter List. Also randomises their values.
	 *
	 * @param number number of parameters to create
	 * @param min    the minimum value of the sliders
	 * @param max    the maximum value
	 */
	protected void initializeParams(int number, double min, double max) {
		for (int i = 0; i < number; i++) {
			Parameter dp = new Parameter(0, "Parameter " + (i + 1), min, max);
			parameters.add(dp);
		}
		randomizeParams();
	}

	/**
	 * Randomizes the values of the parameters.
	 * The values are uniformly distributed between each parameter's minimum and maximum values.
	 */
	public final void randomizeParams() {
		for (Parameter p : parameters) {
			double r = p.getMax() - p.getMin();
			p.set(random() * r + p.getMin());
		}
	}

	/**
	 * @return the list of adjustable parameters this transformation has
	 */
	public final List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * Returns the number of dimensions for the points this transformation handles.
	 * It must be ensured that no fewer dimensions are passed on to the transform method
	 */
	protected abstract int getDimensions();

	/**
	 * "Runs" the transformation. The parameter represents an n-dimensional point. n should not be less than the value of getDimensions()
	 *
	 * @param src
	 * @return
	 */
	public final double[] transform(double[] src) {
		return validify(src, doTransform(src));
	}

	/**
	 * Performs the transformation.
	 * The method may assume that the input point (array) has at leas as many dimensions (elements) as the return value of <code>getDimensions</code>.
	 *
	 * @param src
	 * @return
	 */
	protected abstract double[] doTransform(double[] src);

	/**
	 * Ensures that the value returned has the same length as the <code>src</code> parameter.
	 * If the length of the <code>gen</code> parameter matches the length of the <code>src</code> parameter, <code>gen</code> is returned.
	 * Otherwise, an array of the size of <code>src</code> is returned, with as many values matching <code>gen</code> as possible. If there are too many values, they are dropped. If there are not enough values, the remaining values are filled with the contents of <code>src</code>
	 *
	 * @param src the original array, whose length is to be matched in the return value
	 * @param gen the transformed array, whose values are to be used as much as possible
	 * @return an array that fits the aforementioned specifications
	 */
	double[] validify(double[] src, double[] gen) {
		if (gen.length == src.length) return gen;

		double[] res = new double[src.length];

		if (gen.length > src.length) {
			System.arraycopy(src, 0, res, 0, src.length);
			return res;
		}

		for (int i = 0; i < src.length; i++) {
			res[i] = i < gen.length ? gen[i] : src[i];
		}
		return res;
	}
}
