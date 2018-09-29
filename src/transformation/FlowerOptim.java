package transformation;

import static java.lang.Math.atan;
import static util.Rough.pseudoSin;

/**
 * Three dimensions for space, and one for color
 */
public class FlowerOptim extends Transformation {

	public FlowerOptim() {
		initializeParams(8, -2, 2);
	}

	@Override
public 	String transformationName() {
		return "Optimized Symmetric Flower";
	}

	@Override
	protected int getDimensions() {
		return 4;
	}

	@Override
	public double[] doTransform(double[] src) {

		return new double[]{
				p(0) * pseudoSin(src[1])
						+ p(1) * pseudoSin(src[2]),
				p(2) * pseudoSin(src[2])
						+ p(3) * atan(src[3]),
				p(4) * pseudoSin(src[3])
						+ p(5) * pseudoSin(src[0]),
				p(6) * pseudoSin(src[0])
						+ p(7) * pseudoSin(src[1])};
	}
}
