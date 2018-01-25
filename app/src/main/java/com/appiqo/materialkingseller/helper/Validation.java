package com.appiqo.materialkingseller.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Warlock on 9/30/2017.
 */

public class Validation {

    public static boolean emailValidator(String email) {
        if (email.matches("")) {
            return false;
        } else {
            Pattern pattern;
            Matcher matcher;
            final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
    public static boolean nameValidator(String name) {
        if (name.matches("")) {
            return false;
        }else if (name.length()<=2){
            return false;
        } else {
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile("[a-zA-Z0-9\\-'\\s]++");
            matcher = pattern.matcher(name);
            return matcher.matches();
        }
    }
    public static boolean urlValidator(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }
    public static boolean nullValidator(String value) {
       if (value.matches("")){
           return true;
       }else {
           return false;
       }
    }

    public static boolean passValidator(String pass) {
        if (pass.matches("")) {
            return false;
        } else if (pass.length() < 6) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean confirmPassValidator(String pass, String cpass) {
        if (cpass.matches("")){
            return false;
        }else if (pass.matches(cpass)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean termsValidator(CheckBox checkBox) {
        return checkBox.isChecked();
    }

    public static boolean mobileValidator(String phone) {
        if (phone.matches("")){
            return false;
        }else if (phone.length()<10){
            return false;
        }else {
            return true;
        }
    }

    public static class generalTextWatcher implements TextWatcher {
        EditText editText;
        String type;
        public generalTextWatcher(EditText editText, String type) {
            this.editText = editText;
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length()>0){
                if (type.matches("email")) {
                    if (!Validation.emailValidator(s.toString())) {
                        editText.setError("Enter valid email");
                    }
                }else if (type.matches("pass")) {
                    if (!Validation.passValidator(s.toString())) {
                        editText.setError("Please enter valid password with minimum 6 character");
                    }
                }else if (type.matches("name")) {
                    if (!Validation.nameValidator(s.toString())) {
                        editText.setError("Please enter valid name with only alphabets");
                    }
                }else if (type.matches("phone")) {
                    if (!Validation.mobileValidator(s.toString())) {
                        editText.setError("Please enter valid Phone number");
                    }
                }else if (type.matches("url")) {
                    if (!Validation.urlValidator(s.toString())) {
                        editText.setError("Please enter valid Url");
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
