package transformation.helper;

import transformation.Transformation;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Rotates the point, affecting only the x and y (the first two dimensions) coordinates of the point.
 */
public class Rotate extends Transformation {

	public Rotate() {
		super();
		initializeParams(1, -180, 180);
		parameters.get(0).set(0);
		parameters.get(0).setName("Angle");
	}

	@Override
	public String transformationName() {
		return "Rotate";
	}

	@Override
	protected int getDimensions() {
		return 2;
	}

	@Override
	protected double[] doTransform(double[] src) {
		double a = p(0) / 360 * Math.PI * 2;
		return new double[]{
				cos(a) * src[0] + sin(a) * src[1],
				cos(a) * src[1] - sin(a) * src[0]
		};
	}
}
