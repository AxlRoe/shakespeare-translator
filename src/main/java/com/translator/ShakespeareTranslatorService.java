package com.translator;

public class ShakespeareTranslatorService {
    public String translate(String text) {
        if (text.contains("charizard")) {
            return "Mine most wondrous pokemon charizard";
        } else if (text.contains("butterfree")) {
            return "Mine most wondrous pokemon butterfree";
        }

        return text;
    }
}
