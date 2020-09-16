package com.translator.controller;

import com.translator.service.PokemonTranslatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PokemonTranslatorController.class)
class PokemonTranslatorControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonTranslatorService service;

    @Test
    @DisplayName("Given a valid pokemon, return translated description")
    public void givenPokemonName_thenReturn_translatedDescription () throws Exception {
        when(service.translate("charizard")).thenReturn(Optional.of("Mine most wondrous pokemon charizard"));
        this.mockMvc.perform(get("/pokemon/{name}", "charizard"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.description").value("Mine most wondrous pokemon charizard"))
                .andDo(print());
    }

    @Test
    @DisplayName("Given a not existent pokemon, return Not found response")
    public void givenNotExistentPokemon_thenReturnNoDataFound () throws Exception {
        when(service.translate("foo")).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/pokemon/{name}", "foo"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.description").value(""))
                .andDo(print());
    }
}
