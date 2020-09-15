package com.translator;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PokemonDescriptionProviderService {

    public Optional<String> getPokemonDescription(String pokemonName) {

        if (pokemonName == null || "".equals(pokemonName)) {
            throw new IllegalArgumentException("Ivalida pokemon name");
        }

        if ("charizard".equals(pokemonName)) {
            return Optional.of("My amazing charizard");
        } else if ("butterfree".equals(pokemonName)) {
            return Optional.of("My amazing butterfree");
        }

        return Optional.empty();
    }
}
