package gui.helpers;

import java.text.DecimalFormat;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author docma436
 */
public class ValidationHelper {

    public void addTypeFormatter(JFormattedTextField textField,
        String format, Class<? extends Number> type) {

        // create a format object using our desired format
        DecimalFormat df = new DecimalFormat(format);

        // create a formatter object that will apply the format
        NumberFormatter formatter = new NumberFormatter(df);

        // define the type that the formatter will return
        formatter.setValueClass(type);

        // don't allow the user to enter values that don't match our format
        formatter.setAllowsInvalid(false);

        // create a factory for the formatter
        DefaultFormatterFactory factory
                = new DefaultFormatterFactory(formatter);

        // install the factory in the text field
        textField.setFormatterFactory(factory);
    }

}
