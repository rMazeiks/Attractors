package util;

import elements.Parameter;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that contains code I shouldn't have to write.
 */
public class CommonJava {
	/**
	 * Adapted from https://stackoverflow.com/a/28288641/9074921
	 *
	 * @param original
	 * @return
	 */
	public static HashMap<String, Parameter> clone(
			HashMap<String, Parameter> original) {

		HashMap<String, Parameter> copy = new HashMap<String, Parameter>();
		for (Map.Entry<String, Parameter> entry : original.entrySet()) {
			copy.put(entry.getKey(),
					// Or whatever List implementation you'd like here.
					entry.getValue().clone());
		}
		return copy;
	}
}
