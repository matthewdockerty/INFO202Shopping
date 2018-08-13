package gui.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

/**
 *
 * @author docma436
 */
public class ValidationHelper {

    public void addPatternFormatter(JFormattedTextField textField, Pattern regex, String tooltip) {
        PatternFormatter formatter = new PatternFormatter(regex);
        formatter.setAllowsInvalid(false);
        textField.setFormatterFactory(new DefaultFormatterFactory(formatter));

        // Key listener to show tooltip on key typed.
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                ToolTipManager.sharedInstance().setInitialDelay(250);
                ToolTipManager.sharedInstance().setDismissDelay(2500);

                ToolTipManager.sharedInstance().mouseMoved(
                        new MouseEvent(textField, MouseEvent.MOUSE_MOVED,
                                System.currentTimeMillis(), 0, 1, -2 * textField.getHeight(), 0, false));
            }

            @Override
            public void keyPressed(KeyEvent ke) {}

            @Override
            public void keyReleased(KeyEvent ke) {}
        });

        // Focus listener to enable & disable tooltip.
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                textField.setToolTipText(tooltip);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                textField.setToolTipText(null);
            }
        });
    }

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

    public boolean isObjectValid(Object domain) {

        // create Oval validator
        Validator validator = new Validator();

        // validate the object
        List<ConstraintViolation> violations = validator.validate(domain);

        // were there any violations?
        if (violations.isEmpty()) {
            // nope
            return true;
        } else {
            // yes, so show constraint messages to user
            StringBuilder message = new StringBuilder();

            //	loop through the violations extracting the message for each
            for (ConstraintViolation violation : violations) {
                message.append(violation.getMessage()).append("\n");
            }

            // show a message box to the user with all the violation messages
            JOptionPane.showMessageDialog(null, message.toString(),
                    "Input Problem", JOptionPane.WARNING_MESSAGE);

            return false;
        }
    }

}
