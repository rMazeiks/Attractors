package transformation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.random;

public abstract class Transformation {
	List<DoubleProperty> parameters;

	Transformation() {
		parameters = new ArrayList<>();
	}

	static String transformationName() {
		return "Abstract transformation";
	}

	double p(int index) { // convenience function to save typing
		return parameters.get(index).get();
	}

	void initializeParams(int number) {
		for (int i = 0; i < number; i++) {
			DoubleProperty dp = new SimpleDoubleProperty();
			parameters.add(dp);
		}
		randomizeParams();
	}

	public final void randomizeParams() {
		for (DoubleProperty d : parameters) {
			double v = random() + 1;
			d.set(random() > 0.5 ? v : -v);
		}
	}

	public final List<DoubleProperty> getParameters() {
		return parameters;
	}

	/**
	 * Returns the number of dimensions for the points this transformation handles.
	 */
	abstract int getDimensions();

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
