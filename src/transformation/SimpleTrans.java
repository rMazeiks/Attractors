package transformation;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class SimpleTrans extends Transformation {
	public SimpleTrans() {
		super();
		initializeParams(8, -2, 2);
	}

	@Override
	public String transformationName() {
		return "Simple transformation";
	}

	@Override
	protected int getDimensions() {
		return 2;
	}

	@Override
	public double[] doTransform(double[] src) {

		return new double[]{
				p(0) * sin(src[0] * p(4))
						- p(1) * cos(src[1] * p(5)),
				p(2) * sin(src[0] * p(6))
						+ p(3) * sin(src[1] * p(7))
		};
	}
}
