package com.translator;

public class PokemonDescriptionProviderService {
    public String getPokemonDescription(String pokemonName) {
        if ("charizard".equals(pokemonName)) {
            return "My amazing charizard";
        } else if ("butterfree".equals(pokemonName)) {
            return "My amazing butterfree";
        }

        return "No pokemon found";
    }
}
