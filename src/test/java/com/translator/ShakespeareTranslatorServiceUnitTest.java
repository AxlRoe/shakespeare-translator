package com.translator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShakespeareTranslatorServiceUnitTest {

    ShakespeareTranslatorService service;

    @BeforeEach
    void setUp () {
        service = new ShakespeareTranslatorService();
    }

    @Test
    @DisplayName("As User I want to see a shakespearean translation")
    public void givenAText_translateIt_asShakespeareWouldDo () {
        String translation = service.translate("My Amazing pokemon charizard");
        assertThat(translation).isEqualTo("Mine most wondrous pokemon charizard");

        translation = service.translate("My Amazing pokemon butterfree");
        assertThat(translation).isEqualTo("Mine most wondrous pokemon butterfree");

        translation = service.translate("My Amazing pokemon foo");
        assertThat(translation).isEqualTo("My Amazing pokemon foo");
    }

    @Test
    @DisplayName("Given null text to translate throw exception")
    public void givenIvalidTextToBeTranslated_throwIllegalArgumentException () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.translate(null));
    }
}
