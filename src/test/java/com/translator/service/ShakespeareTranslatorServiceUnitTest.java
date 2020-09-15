package com.translator.service;

import com.translator.exception.ShakespearTranslatorException;
import com.translator.transport.dto.TranslationCallStatus;
import com.translator.transport.dto.TranslationContent;
import com.translator.transport.dto.TranslationDTO;
import com.translator.transport.rest.TranslatorApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShakespeareTranslatorServiceUnitTest {

    @Mock
    private TranslatorApi translatorApi;

    ShakespeareTranslatorService service;

    @BeforeEach
    void setUp () {
        service = new ShakespeareTranslatorService(translatorApi);
    }

    @Test
    @DisplayName("As User I want to see a shakespearean translation")
    public void givenAText_translateIt_asShakespeareWouldDo () throws ShakespearTranslatorException {
        when(translatorApi.getTranslation("My Amazing pokemon charizard")).thenReturn(buildResponse("Mine most wondrous pokemon charizard"));
        String translation = service.translate("My Amazing pokemon charizard");
        assertThat(translation).isEqualTo("Mine most wondrous pokemon charizard");

        when(translatorApi.getTranslation("My Amazing pokemon butterfree")).thenReturn(buildResponse("Mine most wondrous pokemon butterfree"));
        translation = service.translate("My Amazing pokemon butterfree");
        assertThat(translation).isEqualTo("Mine most wondrous pokemon butterfree");

        when(translatorApi.getTranslation("My Amazing pokemon foo")).thenReturn(buildResponse("My Amazing pokemon foo"));
        translation = service.translate("My Amazing pokemon foo");
        assertThat(translation).isEqualTo("My Amazing pokemon foo");
    }

    @Test
    @DisplayName("Given null text to translate throw exception")
    public void givenIvalidTextToBeTranslated_throwIllegalArgumentException () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.translate(null));
    }

    private ResponseEntity<TranslationDTO> buildResponse(String msg) {
        TranslationCallStatus cs = new TranslationCallStatus();
        cs.setTotal(1);

        TranslationContent content = new TranslationContent();
        content.setText("test");
        content.setTranslated(msg);
        content.setTranslation("shakespeare");

        TranslationDTO dto = new TranslationDTO();
        dto.setContents(content);

        return ResponseEntity.ok(dto);
    }
}
