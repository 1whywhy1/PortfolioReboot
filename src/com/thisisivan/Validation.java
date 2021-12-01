package com.thisisivan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class Validation {
    //region Validate Email
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        if (email == null)
        {
            return false;
        }
        return pattern.matcher(email).matches();
    }
    //endregion

    //region Numeric
//    public boolean NumericCheck( String str)
//    {
//        return (Integer.parseInt(str) ? true:false);
//    }
    //endregion

    //region Validate username against empty and pattern
    public static boolean isValidName(String  name)
    {
        // Regex to check valid username.
        String regex = "^[A-Za-z]\\w{1,29}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        if (name == null) {
            return false;
        }

        // Find match between username and the pattern
        Matcher m = p.matcher(name);

        // Return true if there is a match
        return m.matches();
    }
    //endregion

    //region ValidatePassword
    public static boolean isValidPassword(String password)
    {

        // Regex to check valid password
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{2,29}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        // Find match between password and the pattern
        Matcher m = p.matcher(password);

        // Return true if there is a match
        return m.matches();
    }
    //endregion
}
