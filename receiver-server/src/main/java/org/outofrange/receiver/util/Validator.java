package org.outofrange.receiver.util;

import org.outofrange.receiver.exceptions.ValidatorException;

import java.util.regex.Pattern;

/**
 * @author outofrange
 */
public class Validator {
    public static void noSpecialCharacters(String s) {
        Pattern p = Pattern.compile("[A-Z0-9]+(\\.[A-Z0-9]+)?", Pattern.CASE_INSENSITIVE);
        if (!p.matcher(s).matches()) {
            throw new ValidatorException("You must use normal, alphanumeric characters! One dot, followed by other alphanumeric characters is allowed.");
        }
    }
}
