package com.example.enildsbet.helper;

import android.widget.EditText;

public class InputHelper {

    public static String readStringEditText(EditText editText) {
        return editText.getText().toString();
    }

    public static float readDecimalEditText(EditText editText) {
        return InputHelper.convertStringToFloat(editText.getText().toString());
    }

    public static int readIntegerEditText(EditText editText) {
        return InputHelper.convertStringToInteger(editText.getText().toString());
    }

    public static float convertStringToFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int convertStringToInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
