package transformation;

import transformation.helper.Rotate;
import transformation.helper.Scale;
import transformation.helper.Translate;

import java.lang.reflect.InvocationTargetException;

public enum AvailableTransformations {
	SIMPLE(new SimpleTrans()), // todo someone please tell me there's a better way to do this.
	FOUR(new FourDimWeird()),
	EXP(new FourDimWeird2()),
	FLOWER(new Flower()),
	FLOWER_OPT(new FlowerOptim()),

	ROTATE(new Rotate()),
	SCALE(new Scale()),
	TRANSLATE(new Translate());


	Transformation base;

	AvailableTransformations(Transformation t) {
		base = t;
	}

	public String getName() {
		return base.transformationName();
	}

	public Transformation make() {
		try {
			return base.getClass().getConstructor().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
