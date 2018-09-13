package transformation;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Rotate extends Transformation {

	public Rotate()  {
		super();
		initializeParams(1);
		parameters.get(0).set(0);
	}
	@Override
	int getDimensions() {
		return 2;
	}

	@Override
	protected double[] doTransform(double[] src) {
		return new double[]{
				cos(p(0))*src[0] + sin(p(0))*src[1],
				cos(p(0))*src[1] - sin(p(0))*src[0]
		};
	}
}
