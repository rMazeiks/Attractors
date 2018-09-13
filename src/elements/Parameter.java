package elements;

import javafx.beans.property.SimpleDoubleProperty;

public class Parameter extends SimpleDoubleProperty {
	private double min, max;
	private boolean hasMin, hasMax;


	public boolean hasMin() {
		return hasMin;
	}

	public boolean hasMax() {
		return hasMax;
	}

	public Parameter(double initialValue, String name, double min, double max) {
		super(initialValue, name);
		if (min > max) throw new RuntimeException("min > max");
		this.min = min;
		this.max = max;
		hasMax = hasMin = true;


		this.getName();
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
