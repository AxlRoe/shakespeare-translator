package com.translator;

import com.translator.exception.PokemonDescriptionProviderException;
import com.translator.service.PokemonDescriptionProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PokemonTranslatorServiceUnitTest {

    @Mock
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
    public void givenPokemonName_getAShakespearean_description() throws PokemonDescriptionProviderException {
        when(pokemonDescriptionProviderService.getPokemonDescription("charizard")).thenReturn(Optional.of("My amazing pokemon charizard"));
        Optional<String> translation = service.translate("charizard");
        assertThat(translation).isEqualTo(Optional.of("Mine most wondrous pokemon charizard"));

        when(pokemonDescriptionProviderService.getPokemonDescription("butterfree")).thenReturn(Optional.of("My amazing pokemon butterfree"));
        translation = service.translate("butterfree");
        assertThat(translation).isEqualTo(Optional.of("Mine most wondrous pokemon butterfree"));

        translation = service.translate("foo");
        assertThat(translation.isEmpty()).isTrue();
    }

}
