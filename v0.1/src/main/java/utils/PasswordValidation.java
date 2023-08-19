package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {
    public static boolean validation(String password){
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{Punct}])[a-zA-Z\\d\\p{Punct}]{9,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find() && password.equals(matcher.group());
    }
}
