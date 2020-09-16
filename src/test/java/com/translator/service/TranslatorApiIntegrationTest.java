package com.translator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.translator.transport.dto.TranslationContent;
import com.translator.transport.dto.TranslationDTO;
import com.translator.transport.rest.TranslatorApi;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {3001})
@SpringBootTest
@TestPropertySource(locations = "classpath:test-config.properties")
public class TranslatorApiIntegrationTest {

    @Autowired
    private TranslatorApi translatorApi;

    private MockServerClient mockServer;

    public TranslatorApiIntegrationTest(ClientAndServer client) {
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
    @DisplayName("Test Rest Call executed by feign client to retrieve shakesperean translation")
    public void testRestCall_toRetrieveShakespereanTranslation() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        TranslationDTO expectedResponse = new TranslationDTO();
        TranslationContent content = new TranslationContent();
        content.setTranslated("foo");
        expectedResponse.setContents(content);

        this.mockServer.when(HttpRequest
                .request()
                .withMethod("GET")
                .withPath("/translate/shakespeare.json")
                .withQueryStringParameter("text","foo"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(json(objectMapper.writeValueAsString(expectedResponse))));

        TranslationDTO actualResponse = translatorApi.getTranslation("foo").getBody();
        assertThat(actualResponse.getContents().getTranslated()).isEqualTo("foo");
    }
}
