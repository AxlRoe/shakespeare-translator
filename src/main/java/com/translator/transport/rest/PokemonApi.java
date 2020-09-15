package com.translator.transport.rest;

import com.translator.transport.dto.PokemonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "pokemon", url = "${pokemon.url}")
public interface PokemonApi {

    @RequestMapping(method = RequestMethod.GET, value = "/characteristic/{id}")
    ResponseEntity<PokemonDTO> getPokemonDescription(@PathVariable("id") String id);
}
