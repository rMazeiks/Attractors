package transformation;

import javafx.beans.property.DoubleProperty;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Three dimensions for space, and one for color
 */
public class FourDimWeird extends Transformation {

	public FourDimWeird() {
		initializeParams(8, -2, 2);
	}

static 	String transformationName() {
		return "Four-Dimension Weird";
	}

	@Override
	int getDimensions() {
		return 4;
	}

	@Override
	public double[] doTransform(double[] src) {

		return new double[]{
				p(0)*sin(src[1])
						+ p(1)*sin(src[2]),
				p(2)*sin(src[2])
						+ p(3)*cos(src[3]),
				p(4)*sin(src[3])
						+ p(5)*sin(src[0]),
				p(6)*sin(src[0])
						+ p(7)*sin(src[1])};
	}
}
