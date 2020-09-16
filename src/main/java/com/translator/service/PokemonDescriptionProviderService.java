package com.translator.service;

import com.translator.exception.PokemonDescriptionProviderException;
import com.translator.transport.dto.Description;
import com.translator.transport.dto.PokemonDTO;
import com.translator.transport.dto.PokemonDescriptionDTO;
import com.translator.transport.rest.PokemonApi;
import feign.FeignException;
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

        try {
            ResponseEntity<PokemonDTO> respPokemon = pokemonApi.getPokemon(pokemonName);
            ResponseEntity<PokemonDescriptionDTO> respDesc = pokemonApi.getPokemonDescription(respPokemon.getBody().getId());
            return respDesc.getBody().getDescriptions().stream()
                    .filter(d -> "en".equals(d.getLanguage().getName()))
                    .map(Description::getDescription)
                    .findFirst();

        } catch (FeignException.NotFound e) {
            log.error(e.getMessage(), e.getCause());
            String errMsg = String.format("Unknown specified pokemon %s", pokemonName);
            throw new PokemonDescriptionProviderException(errMsg);

        } catch (FeignException e) {
            log.error(e.getMessage(), e.getCause());
            String errMsg = String.format("Something went wrong during retrieving pokemon description: %s", e.getMessage());
            throw new PokemonDescriptionProviderException(errMsg);
        }
    }
}
