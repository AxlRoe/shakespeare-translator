package com.translator.service;

import com.translator.exception.PokemonDescriptionProviderException;
import com.translator.transport.dto.Description;
import com.translator.transport.dto.PokemonDTO;
import com.translator.transport.rest.PokemonApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PokemonDescriptionProviderService {

    private PokemonApi pokemonApi;

    public Optional<String> getPokemonDescription(String pokemonName) throws PokemonDescriptionProviderException {
        if (pokemonName == null || "".equals(pokemonName)) {
            throw new IllegalArgumentException("Invalid pokemon name");
        }

        ResponseEntity<PokemonDTO> response = pokemonApi.getPokemonDescription(pokemonName);
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            return response.getBody().getDescriptions().stream()
                    .filter(d -> "en".equals(d.getLanguage().getName()))
                    .map(Description::getDescription)
                    .findFirst();
        } else if (HttpStatus.NOT_FOUND.equals(response.getStatusCode())) {
            return Optional.empty();
        } else {
            String errMsg = String.format("Something went wrong during retrieving pokemon description {}", response.getStatusCode());
            throw new PokemonDescriptionProviderException(errMsg);
        }
    }
}
