package com.example.enildsbet.helper;

import java.text.NumberFormat;

public class CurrencyHelper {

    public static String convertFloatToRealCurrency(float value) {

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        numberFormat.setMaximumFractionDigits(2);

        return numberFormat.format(value);

    }

}
