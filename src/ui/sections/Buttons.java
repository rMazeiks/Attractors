package ui.sections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import transformation.Transformation;
import ui.Exporter;
import ui.Main;

class Buttons extends HBox {
	private final Main main;

	public Buttons(Main main) {
		this.main = main;

		Exporter e = new Exporter(main);

		Button save = new Button("Save");
		save.setOnAction(event -> e.open());

		Button random = new Button("Randomize parameters");
		random.setOnAction(event -> {
			for (Transformation transformation : main.getTransformations()) {
				transformation.randomizeParams();
			}
		});

		Button stop = new Button("Stop rendering");
		stop.setOnAction(event -> main.stopRender());

		getChildren().addAll(save, random, stop);
	}
}
