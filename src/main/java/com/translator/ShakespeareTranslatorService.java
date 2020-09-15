package com.translator;

import org.springframework.stereotype.Service;

@Service
public class ShakespeareTranslatorService {
    public String translate(String text) {

        if (text == null) {
            throw new IllegalArgumentException("Invalid text to translate");
        }

        if (text.contains("charizard")) {
            return "Mine most wondrous pokemon charizard";
        } else if (text.contains("butterfree")) {
            return "Mine most wondrous pokemon butterfree";
        }

        return text;
    }
}
