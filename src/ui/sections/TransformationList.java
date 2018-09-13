package ui.sections;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import transformation.Transformation;
import ui.Main;
import ui.TransformationAdder;


class TransformationList extends VBox {
	private ListView<Transformation> listView;

	public TransformationList(Main main) {
		super();

		ObservableList<Transformation> transformations = main.getTransformations();

		listView = new ListView<>();
		listView.setItems(transformations);

		MultipleSelectionModel<Transformation> selectionModel = listView.getSelectionModel();

		TransformationAdder adder = new TransformationAdder(main);

		HBox buttons = new HBox();
		{
			Button add = new Button("+");
			add.setOnAction(event -> {
				adder.open();
			});

			Button remove = new Button("-");
			remove.setOnAction(event -> {
				transformations.remove(selectionModel.getSelectedItem());
			});

			Button moveUp = new Button("Up");
			moveUp.setOnAction(event -> {
				int i = selectionModel.getSelectedIndex();
				if (i > 0) {
					Transformation temp = transformations.get(i - 1);
					transformations.set(i - 1, transformations.get(i));
					transformations.set(i, temp);
					selectionModel.select(i - 1);
				}
			});

			Button moveDown = new Button("Down");
			moveDown.setOnAction(event -> {
				int i = selectionModel.getSelectedIndex();
				if (i < transformations.size() - 1) {
					Transformation temp = transformations.get(i + 1);
					transformations.set(i + 1, transformations.get(i));
					transformations.set(i, temp);
					selectionModel.select(i + 1);
				}
			});

			buttons.getChildren().addAll(add, remove, moveUp, moveDown);
		}

		getChildren().addAll(listView, buttons);
	}

	public MultipleSelectionModel<Transformation> getSelectionModel() {
		return listView.getSelectionModel();
	}
}
