package ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import transformation.AvailableTransformations;
import transformation.Transformation;


public class TransformationAdder extends Stage {
	Main main;
	boolean windowCreated = false;

	public TransformationAdder(Main main) {
		this.main = main;
	}

	public void open() {
		if (!windowCreated) createWindow();
		windowCreated = true;

		show();
	}

	private void createWindow() {
		VBox buttons = new VBox();
		for (AvailableTransformations a : AvailableTransformations.values()) {
			Button button = new Button(a.getName());
			button.setOnAction(event -> {
				Transformation newOne = a.make();
				main.addTransformation(newOne);
				this.close();
			});
			buttons.getChildren().add(button);
		}
		setScene(new Scene(buttons));
	}
}
