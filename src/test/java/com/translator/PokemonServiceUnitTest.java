package com.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PokemonServiceUnitTest {

    PokemonService service;

    @BeforeEach
    void setUp () {
        service = new PokemonService();
    }

    @Test
    @DisplayName("As user I want to retrieve pokemon's description")
    public void givenPokemonName_retrieveItsDescription () {
        String description = service.getPokemonDescription("charizard");
        assertThat(description).isEqualTo("My amazing charizard");

        description = service.getPokemonDescription("butterfree");
        assertThat(description).isEqualTo("My amazing butterfree");
    }

}
