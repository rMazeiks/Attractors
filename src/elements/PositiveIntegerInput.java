package elements;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Provides the possibility to create text fields that only accept integer inputs.
 * Invalid characters cannot be typed inside the text field.
 * Copy-pasted (with modifications) from https://stackoverflow.com/a/36749659
 */
public class PositiveIntegerInput implements UnaryOperator<TextFormatter.Change> {
	private final static Pattern DIGIT_PATTERN = Pattern.compile("\\d*");

	private static TextFormatter<Integer> getFormatter(IntegerProperty integerProperty) {
		TextFormatter<Integer> ans = new TextFormatter<>(
				new IntegerStringConverter(), // Standard converter form JavaFX
				integerProperty.get(),
				new PositiveIntegerInput());
		ans.valueProperty().bindBidirectional(integerProperty.asObject());
		return ans;
	}

	/**
	 * Creates a text field that only accepts positive integer values. Binds the specified IntegerProperty to the contents of the TextField.
	 *
	 * @param def
	 * @return
	 */
	public static TextField makeTextField(IntegerProperty def)  {
		IntTextField tf = new IntTextField(def);
		tf.setTextFormatter(PositiveIntegerInput.getFormatter(def));
		return tf;
	}

	@Override
	public TextFormatter.Change apply(TextFormatter.Change t) {
		return DIGIT_PATTERN.matcher(t.getText()).matches() ? t : null;
	}

	private static class IntTextField extends TextField{
		IntegerProperty bound;
		public  IntTextField(IntegerProperty def)  {
			bound = def;
			textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					bound.setValue(Integer.parseInt(newValue));
				}
			});
		}


	}
}