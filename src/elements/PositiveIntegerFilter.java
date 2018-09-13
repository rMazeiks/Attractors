package elements;

import javafx.beans.property.IntegerProperty;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Copy-pasted (with modifications) from https://stackoverflow.com/a/36749659
 */
public class PositiveIntegerFilter implements UnaryOperator<TextFormatter.Change> {
	private final static Pattern DIGIT_PATTERN = Pattern.compile("\\d*");

	public static TextFormatter<Integer> getFormatter(IntegerProperty integerProperty) {
		TextFormatter<Integer> ans = new TextFormatter<>(
				new IntegerStringConverter(), // Standard converter form JavaFX
				integerProperty.get(),
				new PositiveIntegerFilter());
		ans.valueProperty().bindBidirectional(integerProperty.asObject());
		return ans;
	}

	@Override
	public TextFormatter.Change apply(TextFormatter.Change t) {
		return DIGIT_PATTERN.matcher(t.getText()).matches() ? t : null;
	}
}