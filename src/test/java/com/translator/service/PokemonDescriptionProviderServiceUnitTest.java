package com.translator.service;

import com.translator.exception.PokemonDescriptionProviderException;
import com.translator.transport.dto.Description;
import com.translator.transport.dto.Language;
import com.translator.transport.dto.PokemonDTO;
import com.translator.transport.rest.PokemonApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonDescriptionProviderServiceUnitTest {

    @Mock
    private PokemonApi pokemonApi;

    private PokemonDescriptionProviderService service;

    @BeforeEach
    void setUp () {
        service = new PokemonDescriptionProviderService(pokemonApi);
    }

    @Test
    @DisplayName("As user I want to retrieve pokemon's description")
    public void givenPokemonName_retrieveItsDescription () throws PokemonDescriptionProviderException {

        when(pokemonApi.getPokemonDescription("charizard")).thenReturn(buildResponse("My amazing charizard"));
        Optional<String> description = service.getPokemonDescription("charizard");
        assertThat(description).isEqualTo(Optional.of("My amazing charizard"));

        when(pokemonApi.getPokemonDescription("butterfree")).thenReturn(buildResponse("My amazing butterfree"));
        description = service.getPokemonDescription("butterfree");
        assertThat(description).isEqualTo(Optional.of("My amazing butterfree"));

        when(pokemonApi.getPokemonDescription("dummy")).thenReturn(buildEmptyResponse());
        description = service.getPokemonDescription("dummy");
        assertThat(description.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Given null or empty pokemon's name throw exception")
    public void givenInvalidPokemonName_throwIllegalArgumentException () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.getPokemonDescription(null));
    }

    private ResponseEntity<PokemonDTO> buildResponse (String desc) {
        PokemonDTO expectedResponse = new PokemonDTO();
        Language language1 = new Language();
        language1.setName("en");
        language1.setUrl("http://test_url1");

        Language language2 = new Language();
        language2.setName("fr");
        language2.setUrl("http://test_url2");

        Description desc1 = new Description();
        desc1.setLanguage(language1);
        desc1.setDescription(desc);

        Description desc2 = new Description();
        desc2.setLanguage(language2);
        desc2.setDescription("test");

        expectedResponse.setDescriptions(Arrays.asList(desc1, desc2));
        return ResponseEntity.ok(expectedResponse);
    }

    private ResponseEntity<PokemonDTO> buildEmptyResponse () {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PokemonDTO());
    }

}
