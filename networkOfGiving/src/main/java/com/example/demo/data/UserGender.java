package com.example.demo.data;

public enum UserGender {
    Male("Male"),
    Female("Female"),
    Other("Other");

    private String text;
    UserGender(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static UserGender fromString(String text) {
        for (UserGender b : UserGender.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
