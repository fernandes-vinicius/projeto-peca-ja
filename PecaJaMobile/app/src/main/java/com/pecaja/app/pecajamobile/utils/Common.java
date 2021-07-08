package com.pecaja.app.pecajamobile.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vinicius on 22/06/18.
 */

public class Common {

    public static boolean isValidName(String name) {
        String NAME_PATTERN = "^[a-z]{3,45}$";

        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidUsername(String username) {
        String USERNAME_PATTERN = "^[a-z0-9_]{3,20}$";

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[a-z0-9_@]{1,100}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        if (password != null && password.length() >= 4)
            return true;

        return false;
    }

}
