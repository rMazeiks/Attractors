package elements;

import javafx.beans.property.SimpleDoubleProperty;

public class Parameter extends SimpleDoubleProperty {
	private double min, max;
	private boolean hasMin, hasMax;
	private String name; // overriding to implement setName functionality
	private boolean requiresRefresh = true;

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

	public boolean doesRequireRefresh() {
		return requiresRefresh;
	}

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

	public boolean hasMin() {
		return hasMin;
	}

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

	public void clearMin() {
		hasMin = false;
	}

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

	@Override
	public void set(double newValue) {
		if (hasMin && newValue < min) newValue = min;
		if (hasMax && newValue > max) newValue = max;
		super.set(newValue);
	}


}
