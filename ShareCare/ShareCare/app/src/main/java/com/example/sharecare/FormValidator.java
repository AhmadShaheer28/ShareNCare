package com.example.sharecare;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidator {
    public static boolean isTextFieldValid(EditText editText, String errorText) {

        editText.setError(null);
        boolean isValid = true;
        String textValue = editText.getText().toString();
        if (TextUtils.isEmpty(textValue)) {
            editText.setError(errorText);
            isValid = false;
        }
        return isValid;
    }

    public static boolean isTextFieldValid(TextView editText, String errorText) {

        editText.setError(null);
        boolean isValid = true;
        String textValue = editText.getText().toString();
        if (TextUtils.isEmpty(textValue)) {
            editText.setError(errorText);
            isValid = false;
        }
        return isValid;
    }


    public static boolean isNumeric(EditText editText, String errorText) {

        editText.setError(null);
        boolean isValid = true;
        String textValue = editText.getText().toString();
        if (isDigit(textValue)) {
            editText.setError(errorText);
            isValid = false;
        }
        return isValid;
    }

    public static boolean inRange(EditText editText, int min, int max, String errorText) {

        editText.setError(null);
        boolean isValid = true;
        String textValue = editText.getText().toString();
        if (!isInRange(textValue, min, max)) {
            editText.setError(errorText);
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        boolean validPhoneNumber = true;
        final String regex = "^[+][0-9]{10,13}$";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(phoneNumber);

        if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            validPhoneNumber = false;
        }

        if (!matcher.matches()) {
            validPhoneNumber = false;
        }
        return validPhoneNumber;
    }

    public static boolean inRange(EditText editText, double min, double max, String errorText) {

        editText.setError(null);
        boolean isValid = true;
        String textValue = editText.getText().toString();
        if (!isInRange(textValue, min, max)) {
            editText.setError(errorText);
            isValid = false;
        }
        return isValid;
    }

    private static boolean isDigit(String textValue) {

        try {
            Double.parseDouble(textValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isInRange(String textValue, double min, double max) {
        try {
            Double myValue = Double.parseDouble(textValue);
            return min <= myValue && myValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
