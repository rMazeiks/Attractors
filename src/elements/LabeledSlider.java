package elements;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class LabeledSlider extends BorderPane {
	private Slider slider;

	public LabeledSlider(Parameter parameter) {
		slider = new Slider();
		slider.setMin(parameter.getMin());
		setLeft(new Label(parameter.getMin() + ""));
		slider.setMax(parameter.getMax());
		setRight(new Label(parameter.getMax() + ""));

		slider.valueProperty().bindBidirectional(parameter);

		setCenter(slider);

		HBox top = new HBox();
		top.getChildren().add(new Label(parameter.getName() + ": "));
		Label value = new Label();
		value.textProperty().bind(parameter.asString("%.2f"));
		top.getChildren().add(value);
		setTop(top);
	}
}
