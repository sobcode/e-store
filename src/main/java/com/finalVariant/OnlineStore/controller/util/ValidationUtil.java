package com.finalVariant.OnlineStore.controller.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static boolean isLoginValid(String login){
        Matcher m = Pattern.compile("(?=^)[a-zA-Z1-9_!#$%*]*(?=$)").matcher(login);
        return m.find();
    }

    public static boolean isPasswordValid(String password){
        Matcher m = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}").matcher(password);
        return m.find();
    }
}
