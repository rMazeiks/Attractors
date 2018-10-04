package ui.export;

import elements.LabeledSlider;
import elements.Parameter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import util.Adjustable;

import java.io.File;
import java.util.ArrayList;

public class AnimExp extends VBox {
	Exporter exporter;
	ObjectProperty<Adjustable> selectedAdjustable = new SimpleObjectProperty<>();
	ObjectProperty<Parameter> selectedParam = new SimpleObjectProperty<>();

	Parameter start, end, incr;

	public AnimExp(Exporter exporter) {
		this.exporter = exporter;

		Button start = new Button("Export...");
		start.setOnAction(event -> export());

		getChildren().addAll(adjustablePicker(), paramPicker(), paramControl(), start);
		setPrefHeight(400); // todo how do I get rid of this without hiding content
	}

	private StackPane paramControl() {
		StackPane stackPane = new StackPane();

		selectedParam.addListener((observable, oldValue, newValue) -> {
			stackPane.getChildren().removeIf(everything -> true);

			VBox vBox = new VBox();
			double range = newValue.getMax() - newValue.getMin();

			start = new Parameter(
					newValue.get(), "Start",
					newValue.getMin(), newValue.getMax());
			end = new Parameter(
					newValue.get(), "End",
					newValue.getMin(), newValue.getMax());
			incr = new Parameter(
					range / 100, "Increment",
					range / 1000, range / 10);

			vBox.getChildren().addAll(
					new LabeledSlider(start),
					new LabeledSlider(end),
					new LabeledSlider(incr)
			);
			stackPane.getChildren().add(vBox);
		});

		return stackPane;
	}

	private StackPane paramPicker() {
		StackPane s = new StackPane();
		selectedAdjustable.addListener((observable, oldValue, newValue) -> {
			ObservableList<Parameter> obs = FXCollections.observableArrayList(
					newValue.getParameters());
			ComboBox<Parameter> paramBox = new ComboBox<>(obs);

			s.getChildren().removeIf(everything -> true);
			s.getChildren().add(paramBox);

			selectedParam.bind(paramBox.valueProperty());
		});
		return s;
	}

	private VBox adjustablePicker() {
		VBox vBox = new VBox();

		ArrayList<Adjustable> adjustables = new ArrayList<>();
		adjustables.addAll(exporter.getMain().getTransformations());
		adjustables.add(exporter.getMain().getPlotter().get());
		ObservableList<Adjustable> obs = FXCollections.observableArrayList(adjustables);

		ComboBox<Adjustable> adjustableBox = new ComboBox<>(obs);
		vBox.getChildren().add(adjustableBox);

		selectedAdjustable.bind(adjustableBox.valueProperty());

		return vBox;
	}

	private void export() {

		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Export Animation");
		File file = dirChooser.showDialog(exporter);

		if (file == null) return;

		File animFolder = new File(file.getAbsolutePath() + "/animation");
		animFolder.mkdirs();

		AnimRenderer renderer = new AnimRenderer(exporter, selectedParam.get());
		renderer.render(animFolder, start.get(), end.get(), incr.get());

	}
}
