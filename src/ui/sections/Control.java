package ui.sections;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import transformation.Transformation;
import ui.Main;

/**
 * A section of the GUI that contains:
 * - A list representation of the Transformations
 * - Sliders to control the transformation's parameters (when applicable)
 * - The Buttons section
 */
public class Control extends VBox {
	BorderPane sliderPlaceholder;
	private Main main;

	public Control(Main main) {
		super();
		this.main = main;
		TransformationList transformationList = new TransformationList(main);
		this.getChildren().add(transformationList);

		sliderPlaceholder = new BorderPane();
		this.getChildren().add(sliderPlaceholder);
		this.getChildren().add(new Buttons(main));

		transformationList.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> updateSliders(newValue));

		setSpacing(40);
		this.setPadding(new Insets(40));

		updateSliders(transformationList.getSelectionModel().getSelectedItem());
	}

	void updateSliders(Transformation transformation) {
		if (transformation == null) {
			sliderPlaceholder.setCenter(new StackPane()); // empty, todo is there a better way? Can I just remove it?
		} else {
			sliderPlaceholder.setCenter(new Sliders(main, transformation));
		}
	}
}
