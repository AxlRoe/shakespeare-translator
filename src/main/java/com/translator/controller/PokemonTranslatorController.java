package com.translator.controller;

import com.translator.exception.PokemonDescriptionProviderException;
import com.translator.exception.ShakespearTranslatorException;
import com.translator.service.PokemonTranslatorService;
import com.translator.transport.dto.PokemonTranslationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class PokemonTranslatorController {

    private PokemonTranslatorService service;

    @RequestMapping(value = "/pokemon/{name}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity pokemon(@PathVariable("name") String name) {

        if (name == null || "".equals(name)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PokemonTranslationDTO(name,""));
        }

        try {
            return service.translate(name)
                    .map(desc -> ResponseEntity.ok(new PokemonTranslationDTO(name, desc)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PokemonTranslationDTO(name,"")));

        } catch (PokemonDescriptionProviderException | ShakespearTranslatorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
