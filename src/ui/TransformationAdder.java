package ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import transformation.AvailableTransformations;


public class TransformationAdder extends Stage {
	Main main;

	public TransformationAdder(Main main) {
		this.main = main;
	}

	boolean windowCreated = false;
	public void open()  {
		if(!windowCreated) createWindow();
		windowCreated = true;

		show();
	}

	private void createWindow() {
		VBox buttons = new VBox();
		for(AvailableTransformations a: AvailableTransformations.values())  {
			Button button = new Button(a.getName());
			button.setOnAction((event -> {
				main.getTransformations().add(
						a.getInitializer().newTransformation());
				this.close();
			}));
			buttons.getChildren().add(button);
		}
		setScene(new Scene(buttons));
	}
}
