package gui.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author docma436
 */
public class PatternFormatter extends DefaultFormatter {
    
    private Matcher matcher;
    
    public PatternFormatter(Pattern regex) {
        setOverwriteMode(true);
        matcher = regex.matcher("");
    }
    
    public Object stringToValue(String string) throws java.text.ParseException {
        string = string.toUpperCase();
        if (string == null)
            return null;
        
        matcher.reset(string);
        
        if (!matcher.matches())
            throw new java.text.ParseException("String does not match pattern", 0);
        
        return super.stringToValue(string);
    }
    
}
