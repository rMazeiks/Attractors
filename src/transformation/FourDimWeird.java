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
		initializeParams(8);
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
		List<DoubleProperty> p = parameters;

		return new double[]{
				p.get(0).get()*sin(src[1])
						+ p.get(1).get()*sin(src[2]),
				p.get(2).get()*sin(src[2])
						+ p.get(3).get()*cos(src[3]),
				p.get(4).get()*sin(src[3])
						+ p.get(5).get()*sin(src[0]),
				p.get(6).get()*sin(src[0])
						+ p.get(7).get()*sin(src[1])};
	}
}
