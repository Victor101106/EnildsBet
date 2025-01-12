package com.example.enildsbet.model;

public class Balance {

    private float value;

    public Balance (float value) {
        this.value = value;
    }

    public float getBalance() {
        return this.value;
    }

    public void setBalance(float value) {
        this.value = value;
    }

    public boolean balanceContainsValue(float value) {
        return this.value >= value;
    }

    public static boolean validateBalance(float value) {
        return value > 0;
    }

}
