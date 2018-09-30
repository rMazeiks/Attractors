package transformation.helper;

import transformation.Transformation;

/**
 * Scales all the values of the point
 */
public class Scale extends Transformation {

	@Override
	public  String transformationName()  {
		return "Scale";
	}

	public Scale()  {
		super();
		initializeParams(1, 0, 2);
		parameters.get(0).set(1);
		parameters.get(0).setName("Factor");
	}
	@Override
	protected int getDimensions() {
		return 1;
	}

	@Override
	protected double[] doTransform(double[] src) {
		double[] ans = src.clone();
		for (int i = 0; i < ans.length; i++) {
			ans[i]*=p(0);
		}
		return ans;
	}
}
