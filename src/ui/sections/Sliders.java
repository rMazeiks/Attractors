package ui.sections;

import elements.LabeledSlider;
import elements.Parameter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import transformation.Transformation;
import ui.Main;

import java.util.List;

class Sliders extends VBox  {
	private final Main main;

	public Sliders(Main main, Transformation transformation)  {
		this.main = main;
		List<Parameter> params = transformation.getParameters();

		for (int i = 0; i < params.size(); i++) {
			LabeledSlider slider = new LabeledSlider(params.get(i));
			getChildren().add(slider);
		}
		this.setSpacing(10);
	}
}
