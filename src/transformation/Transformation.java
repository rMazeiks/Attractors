package transformation;

import elements.Parameter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import util.Adjustable;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.random;

public abstract class Transformation implements Adjustable {
	protected List<Parameter> parameters;

	protected Transformation() {
		parameters = new ArrayList<>();
	}

	public abstract String transformationName();

	protected double p(int index) { // convenience function to save typing
		return parameters.get(index).get();
	}

	protected void initializeParams(int number, double min, double max) {
		for (int i = 0; i < number; i++) {
			Parameter dp = new Parameter(0, "Parameter "+ (i+1), min, max);
			parameters.add(dp);
		}
		randomizeParams();
	}

	public final void randomizeParams() {
		for (Parameter p : parameters) {
			double v = random() + 1;
			p.set(random() > 0.5 ? v : -v);
		}
	}

	public final List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * Returns the number of dimensions for the points this transformation handles.
	 */
	protected abstract int getDimensions();

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
	 * @param src
	 * @param gen
	 * @return
	 */
	double[] validify(double[] src, double[] gen) {
		if (gen.length == src.length) return gen;

		double[] res = new double[src.length];

		if (gen.length > src.length) {
			System.arraycopy(src, 0, res, 0, src.length);
		}

		for (int i = 0; i < src.length; i++) {
			res[i] = i < gen.length ? gen[i] : src[i];
		}
		return res;
	}
}
