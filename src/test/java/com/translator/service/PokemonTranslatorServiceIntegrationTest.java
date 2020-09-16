package com.translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.translator.exception.PokemonDescriptionProviderException;
import com.translator.exception.ShakespearTranslatorException;
import com.translator.transport.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {3000, 3001})
@SpringBootTest
@TestPropertySource(locations = "classpath:test-config.properties")
public class PokemonTranslatorServiceIntegrationTest {

    @Autowired
    private PokemonTranslatorService service;

    private MockServerClient mockServer;

    public PokemonTranslatorServiceIntegrationTest(ClientAndServer client) {
        this.mockServer = client;
    }

    @BeforeEach
    void setUp(MockServerClient mockServer) throws JsonProcessingException {
        this.mockServer = mockServer;
        Assertions.assertTrue(this.mockServer.hasStarted());
        setupServer();
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }

    @Test
    @DisplayName("Translate a pokemon description using mocked calls")
    public void testTranslation_usingMockedRestCalls() throws ShakespearTranslatorException, PokemonDescriptionProviderException {
        Optional<String> response = service.translate("charizard");
        assertThat(response.get()).isEqualTo("Mine most wondrous pokemon charizard");
    }

    private void setupServer () throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        PokemonDTO expectedPokemon = buildResponseForPokemonService();
        this.mockServer.when(HttpRequest
                .request()
                .withMethod("GET")
                .withPath("/api/v2/characteristic/charizard"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(json(objectMapper.writeValueAsString(expectedPokemon))));

        TranslationDTO expectedTranslation = buildResponseForShakespeareService();
        this.mockServer.when(HttpRequest
                .request()
                .withMethod("GET")
                .withPath("/translate/shakespeare.json")
                .withQueryStringParameter("text","My amazing charizard"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(json(objectMapper.writeValueAsString(expectedTranslation))));
    }

    private TranslationDTO buildResponseForShakespeareService () {
        TranslationDTO expectedResponse = new TranslationDTO();
        TranslationContent content = new TranslationContent();
        content.setTranslated("Mine most wondrous pokemon charizard");
        expectedResponse.setContents(content);
        return expectedResponse;
    }

    private PokemonDTO buildResponseForPokemonService () {

        PokemonDTO expectedResponse = new PokemonDTO();
        Language language1 = new Language();
        language1.setName("en");
        language1.setUrl("http://test_url1");

        Language language2 = new Language();
        language2.setName("fr");
        language2.setUrl("http://test_url2");

        Description desc1 = new Description();
        desc1.setLanguage(language1);
        desc1.setDescription("My amazing charizard");

        Description desc2 = new Description();
        desc2.setLanguage(language2);

        expectedResponse.setDescriptions(Arrays.asList(desc1, desc2));
        return expectedResponse;
    }

}
