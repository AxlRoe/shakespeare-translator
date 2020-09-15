package com.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PokemonTranslatorServiceUnitTest {

    @Autowired
    PokemonDescriptionProviderService pokemonDescriptionProviderService;

    @Autowired
    ShakespeareTranslatorService shakespeareTranslatorService;

    PokemonTranslatorService service;

    @BeforeEach
    void setUp () {
        service = new PokemonTranslatorService(pokemonDescriptionProviderService, shakespeareTranslatorService);
    }

    @Test
    @DisplayName("As user I want a Shakespearean description of my pokemon")
    public void givenPokemonName_getAShakespearean_description() {
        String translation = service.translate("charizard");
        assertThat(translation).isEqualTo("Mine most wondrous pokemon charizard");

        translation = service.translate("butterfree");
        assertThat(translation).isEqualTo("Mine most wondrous pokemon butterfree");

    }

}
