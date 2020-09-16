package com.translator.service;

import com.translator.exception.PokemonDescriptionProviderException;
import com.translator.exception.ShakespearTranslatorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PokemonTranslatorService {

    private PokemonDescriptionProviderService pokemonDescriptionProviderService;
    private ShakespeareTranslatorService shakespeareTranslatorService;

    public Optional<String> translate(String pokemonName) throws PokemonDescriptionProviderException, ShakespearTranslatorException {
        Optional<String> description = pokemonDescriptionProviderService.getPokemonDescription(pokemonName);
        if (!description.isPresent()) {
            return Optional.empty();
        } else {
            String translation = shakespeareTranslatorService.translate(description.get());
            return Optional.of(translation);
        }
    }
}
