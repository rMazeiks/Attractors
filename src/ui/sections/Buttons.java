package ui.sections;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import transformation.Transformation;
import ui.Main;
import ui.export.Exporter;

/**
 * A section of the GUI that contains buttons for saving (exporting) the render, randomizing Parameters, and canceling the render process.
 */
class Buttons extends HBox {
	private final Main main;

	public Buttons(Main main) {
		this.main = main;

		Exporter e = new Exporter(main);

		Button save = new Button("Save");
		save.setOnAction(event -> {
			main.stopRender();
			e.open();
		});

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
