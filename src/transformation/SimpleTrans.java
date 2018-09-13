package transformation;

import javafx.beans.property.DoubleProperty;

import java.util.List;

import static java.lang.StrictMath.*;

public class SimpleTrans extends Transformation {
	public SimpleTrans()  {
		super();
		initializeParams(8);
	}

	static String transformationName() {
		return "Simple transformation";
	}

	@Override
	int getDimensions() {
		return 2;
	}

	@Override
	public double[] doTransform(double[] src) {
		List<DoubleProperty> p = parameters; // alias to save typing

		return new double[] {
			p.get(0).get()*sin(src[0]*p.get(4).get())
					-p.get(1).get()*cos(src[1]*p.get(5).get()),
			p.get(2).get()*sin(src[0]*p.get(6).get())
					+p.get(3).get()*sin(src[1]*p.get(7).get())
		};
	}
}
