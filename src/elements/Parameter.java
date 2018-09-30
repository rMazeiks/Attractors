package elements;

import javafx.beans.property.SimpleDoubleProperty;

/**
 * A SimpleDoubleProperty that ensures its value does not leave the [minimum, maximum] range.
 * It also has a name that can be changed.
 */
public class Parameter extends SimpleDoubleProperty {
	private double min, max;
	private boolean hasMin, hasMax;
	private String name; // overriding to implement setName functionality
	private boolean requiresRefresh = true;

	/**
	 * Initializes the parameter with the specified initial value, name, minimum and maximum values.
	 *
	 * @param initialValue the initial value
	 * @param name         the name of the parameter (to be displayed to the user)
	 * @param min          the minimum allowed value
	 * @param max          the maximum allowed value
	 */
	public Parameter(double initialValue, String name, double min, double max) {
		super(initialValue, name);
		this.name = name;
		if (min > max) throw new RuntimeException("min > max");
		this.min = min;
		this.max = max;
		hasMax = hasMin = true;

		set(initialValue); // todo figure out why this is needed. Sometimes get() didn't initially return the initialValue. weird.
	}

	@Override
	public Parameter clone() {
		return new Parameter(get(), getName(), min, max);
	}

	/**
	 *
	 * @return true if a change in the parameter requires a Plotter's rendering to be restarted from scratch.
	 */
	public boolean doesRequireRefresh() {
		return requiresRefresh;
	}

	/**
	 *
	 * @param requiresRefresh determines whether a change in the parameter requires a Plotter's rendering to be restarted from scratch
	 */
	public void setRequiresRefresh(boolean requiresRefresh) {
		this.requiresRefresh = requiresRefresh;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @return true if a minimum value for the parameter is enforced
	 */
	public boolean hasMin() {
		return hasMin;
	}

	/**
	 *
	 * @return true if a maximum value for the slider is enforced
	 */
	public boolean hasMax() {
		return hasMax;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
		hasMin = true;
	}

	/**
	 * Disables enforcement of the minimum value
	 */
	public void clearMin() {
		hasMin = false;
	}

	/**
	 * Disables enforcement of the maximum value
	 */
	public void clearMax() {
		hasMax = false;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
		hasMax = true;
	}

	/**
	 * Sets the value of the Parameter if the specified value is within the Parameter's range.
	 * Otherwise sets the value to the closest possible value
	 * @param newValue the value to be set
	 */
	@Override
	public void set(double newValue) {
		if (hasMin && newValue < min) newValue = min;
		if (hasMax && newValue > max) newValue = max;
		super.set(newValue);
	}


}
