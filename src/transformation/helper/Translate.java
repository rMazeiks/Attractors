package transformation.helper;

import transformation.Transformation;

/**
 * Translates (shifts) the x and y values of the point (the first 2 dimensions)
 */
public class Translate extends Transformation {

	public Translate() {
		super();
		initializeParams(2, -0.2, 0.2);
		parameters.get(0).set(0);
		parameters.get(0).setName("X-displacement");
		parameters.get(1).set(0);
		parameters.get(0).setName("Y-displacement");
	}

	@Override
	public String transformationName() {
		return "Translate";
	}

	@Override
	protected int getDimensions() {
		return 2;
	}

	@Override
	protected double[] doTransform(double[] src) {
		return new double[]{
				src[0] + p(1),
				src[1] + p(0)
		};
	}
}
