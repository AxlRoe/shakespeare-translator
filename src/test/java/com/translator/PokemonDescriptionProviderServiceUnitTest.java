package com.translator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PokemonDescriptionProviderServiceUnitTest {

    PokemonDescriptionProviderService service;

    @BeforeEach
    void setUp () {
        service = new PokemonDescriptionProviderService();
    }

    @Test
    @DisplayName("As user I want to retrieve pokemon's description")
    public void givenPokemonName_retrieveItsDescription () {
        Optional<String> description = service.getPokemonDescription("charizard");
        assertThat(description).isEqualTo(Optional.of("My amazing charizard"));

        description = service.getPokemonDescription("butterfree");
        assertThat(description).isEqualTo(Optional.of("My amazing butterfree"));

        description = service.getPokemonDescription("dummy");
        assertThat(description.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Given null or empty pokemon's name throw exception")
    public void givenInvalidPokemonName_throwIllegalArgumentException () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.getPokemonDescription(null));
    }

}
