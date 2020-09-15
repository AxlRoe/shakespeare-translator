package com.translator.transport.rest;

import com.translator.transport.dto.TranslationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "translator", url = "${translator.url}")
public interface TranslatorApi {

    @RequestMapping(method = RequestMethod.GET, value = "/translate/shakespeare.json")
    ResponseEntity<TranslationDTO> getTranslation (@RequestParam(name = "text") String text);
}
