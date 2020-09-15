package com.translator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PokemonTranslatorService {

    private PokemonDescriptionProviderService pokemonDescriptionProviderService;
    private ShakespeareTranslatorService shakespeareTranslatorService;

    public String translate(String pokemonName) {
        String description = pokemonDescriptionProviderService.getPokemonDescription(pokemonName);
        String translation = shakespeareTranslatorService.translate(description);
        return translation;
    }
}
