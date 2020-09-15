package com.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        String description = service.getPokemonDescription("charizard");
        assertThat(description).isEqualTo("My amazing charizard");

        description = service.getPokemonDescription("butterfree");
        assertThat(description).isEqualTo("My amazing butterfree");

        description = service.getPokemonDescription("dummy");
        assertThat(description).isEqualTo("No pokemon found");
    }

}
