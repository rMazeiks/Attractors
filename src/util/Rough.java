package util;

/**
 * A class that contains approximations of various functions.
 * These should run faster than the actual functions, but this has not been tested todo
 */
public class Rough {

	/**
	 * A continuous function that kinda looks like the sine wave. I mean, it starts at 0 and then smoothly goes up to 1(at PI/2), and then back to 0 at PI, etc.
	 * It's actually composed of upwards and downwards sloping parabolas, so it should be a bit faster to compute.
	 * Please do not use this for a rotation matrix where precision is required, or anything like that.
	 * Also, don't use it for making calculations to launch rockets. The rockets will crash.
	 *
	 * Maximum deviation from sine: about 0.056
	 *
	 * I figured out the function with the help of Desmos. Thank you, Desmos!!
	 *
	 * @param x the angle, in radians
	 * @return a quick approximation of sine
	 */
	public static double pseudoSin(double x) {
		double t = mod(x / Math.PI, 2); // start at 0, go up to 2 at 2PI, repeat (in both directions, positive and negative).
		return t < 1 ?
				1 - sq(t * 2 - 1) : // the downwards-bent part
				sq(t * 2 - 3) - 1; // the upwards-bent part
	}

	/**
	 * A continuous function that kinda looks like the cosine wave. Based on <code>pseudoSin</code>
	 *
	 * @param x the angle, in radians
	 * @return an approximation of cosine
	 */
	static double pseudoCos(double x) {
		return pseudoSin(x + Math.PI / 2);
	}

	/**
	 * Estimates tangent as <code>pseudoSin/pseudoTan</code>
	 * @param x the angle, in radians
	 * @return an approximation of tangent
	 */
	static double pseudoTan(double x)  {
		return pseudoSin(x)/pseudoCos(x);
	}

	/**
	 * An actual modulo
	 *
	 * @param x
	 * @param m
	 * @return
	 */
	static double mod(double x, double m) {
		double a = x % m;
		if (a < 0) a += m;
		return a;
	}

	static double sq(double x) {
		return x * x;
	}
}
