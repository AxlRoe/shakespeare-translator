package com.translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.translator.transport.dto.Description;
import com.translator.transport.dto.Language;
import com.translator.transport.dto.PokemonDescriptionDTO;
import com.translator.transport.rest.PokemonApi;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {3000})
@SpringBootTest
@TestPropertySource(locations = "classpath:test-config.properties")
public class PokemonApiIntegrationTest {

    @Autowired
    private PokemonApi pokemonApi;

    private MockServerClient mockServer;

    public PokemonApiIntegrationTest(ClientAndServer client) {
        this.mockServer = client;
    }

    @BeforeEach
    void setUp(MockServerClient mockServer) {
        this.mockServer = mockServer;
        Assertions.assertTrue(this.mockServer.hasStarted());
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }

    @Test
    @DisplayName("Test Rest Call executed by feign client to retrieve pokemon description")
    public void testRestCall_toRetrievePokemonDescription() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        PokemonDescriptionDTO expectedResponse = new PokemonDescriptionDTO();
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

        this.mockServer.when(HttpRequest
                .request()
                .withMethod("GET")
                .withPath("/api/v2/characteristic/1"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(json(objectMapper.writeValueAsString(expectedResponse))));

        PokemonDescriptionDTO actualResponse = pokemonApi.getPokemonDescription(1).getBody();
        assertThat(actualResponse.getDescriptions().size()).isEqualTo(2);
    }


}
