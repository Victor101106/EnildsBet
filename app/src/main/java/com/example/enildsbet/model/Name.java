package com.example.enildsbet.model;

public class Name {

    private final String value;

    public Name (String value) {
        this.value = value.trim();
    }

    public String getName() {
        return this.value;
    }

    public static boolean validateName(String value) {
        return value.trim().length() >= 3;
    }

}