package com.translator.service;

import com.translator.exception.ShakespearTranslatorException;
import com.translator.transport.dto.TranslationDTO;
import com.translator.transport.rest.TranslatorApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ShakespeareTranslatorService {

    private TranslatorApi translatorApi;

    public String translate(String text) throws ShakespearTranslatorException {

        if (text == null) {
            throw new IllegalArgumentException("Invalid text to translate");
        }

        ResponseEntity<TranslationDTO> response = translatorApi.getTranslation(text);
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            return response.getBody().getContents().getTranslated();
        } else {
            String errMsg = String.format("Something went wrong during retrieving translation description {}", response.getStatusCode());
            throw new ShakespearTranslatorException(errMsg);
        }
    }
}
