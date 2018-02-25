package com.justinfrasier.emailValidation;

import java.util.regex.Pattern;

public class EmailChecker {

    public boolean validate(String emailAddress){
        String test = "^[_A-Za-z0-9-\\.\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$|" +
                "^\"[_A-Za-z0-9-\\.\\+@]+(\\.[_A-Za-z0-9-]+)*\"@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(test);
        return pattern.matcher(emailAddress).matches();
    }
}
