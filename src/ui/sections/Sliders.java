package ui.sections;

import elements.LabeledSlider;
import elements.Parameter;
import javafx.scene.layout.VBox;
import transformation.Transformation;
import ui.Main;

import java.util.List;

/**
 * A section of the GUI showing sliders that can be used to control the selected Transformation.
 */
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
