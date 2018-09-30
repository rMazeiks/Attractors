package util;

import elements.Parameter;

import java.util.List;

/**
 * An adjustable class is one that has a List of Parameters that can be used to control an instance.
 */
public interface Adjustable {
	List<Parameter> getParameters();
}
