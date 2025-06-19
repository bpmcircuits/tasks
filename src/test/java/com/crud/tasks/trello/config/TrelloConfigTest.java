package com.crud.tasks.trello.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "trello.api.endpoint.prod=https://api.trello.com/1",
        "trello.app.key=1234",
        "trello.app.token=5678",
        "trello.app.username=bartpokrywka"
})
class TrelloConfigTest {

    @Autowired
    TrelloConfig trelloConfig;

    @Test
    void testTrelloConfig() {
        assertNotNull(trelloConfig);
    }

    @Test
    void testGetters() {
        // When
        String endpoint = trelloConfig.getTrelloApiEndpoint();
        String key = trelloConfig.getTrelloAppKey();
        String token = trelloConfig.getTrelloToken();
        String username = trelloConfig.getUsername();

        // Then
        assertNotNull(trelloConfig);
        assertEquals("https://api.trello.com/1", endpoint);
        assertEquals("1234", key);
        assertEquals("5678", token);
        assertEquals("bartpokrywka", username);
    }
}