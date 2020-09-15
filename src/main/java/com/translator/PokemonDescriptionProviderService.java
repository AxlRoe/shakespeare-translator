package com.translator;

public class PokemonDescriptionProviderService {
    public String getPokemonDescription(String pokemonName) {

        if (pokemonName == null || "".equals(pokemonName)) {
            throw new IllegalArgumentException("Ivalida pokemon name");
        }

        if ("charizard".equals(pokemonName)) {
            return "My amazing charizard";
        } else if ("butterfree".equals(pokemonName)) {
            return "My amazing butterfree";
        }

        return "No pokemon found";
    }
}
