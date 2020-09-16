package com.translator.transport.rest;

import com.translator.transport.dto.PokemonDTO;
import com.translator.transport.dto.PokemonDescriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "pokemon", url = "${pokemon.url}")
public interface PokemonApi {

    @RequestMapping(method = RequestMethod.GET, value = "/characteristic/{id}")
    ResponseEntity<PokemonDescriptionDTO> getPokemonDescription(@PathVariable("id") int id);

    @RequestMapping(method = RequestMethod.GET, value = "/pokemon/{name}")
    ResponseEntity<PokemonDTO> getPokemon(@PathVariable("name") String name);

}
