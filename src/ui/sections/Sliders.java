package ui.sections;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import transformation.Transformation;
import ui.Main;

import java.util.List;

class Sliders extends VBox implements ChangeListener {
	private final Main main;

	public Sliders(Main main, Transformation transformation)  {
		this.main = main;
		List<DoubleProperty> params = transformation.getParameters();

		for (int i = 0; i < params.size(); i++) {
			DoubleProperty p = params.get(i);
			Slider slider = new Slider();
			slider.setMax(2);
			slider.setMin(-2);
			slider.setPrefWidth(400);
			slider.valueProperty().bindBidirectional(p);
			VBox box = new VBox();
			box.getChildren().addAll(new Label("Parameter " + (i+1)), slider);
			this.getChildren().add(box);

			slider.valueProperty().addListener(this);
		}
		this.setSpacing(10);
	}

	@Override
	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
		main.update();
	}
}
