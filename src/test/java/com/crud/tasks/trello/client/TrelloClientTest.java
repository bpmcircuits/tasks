package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDTO;
import com.crud.tasks.domain.TrelloBadgesDTO;
import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.domain.TrelloCardDTO;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com/");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getUsername()).thenReturn("test");

        TrelloBoardDTO[] trelloBoards = new TrelloBoardDTO[1];
        trelloBoards[0] = new TrelloBoardDTO("test_id", "Kodilla", new ArrayList<>());

        URI uri = new URI("https://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDTO[].class)).thenReturn(trelloBoards);
        //when
        List<TrelloBoardDTO> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("Kodilla", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    void shouldCreateCard() throws URISyntaxException {
        //given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com/");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        TrelloCardDTO trelloCardDTO = new TrelloCardDTO(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );

        URI uri = new URI(
                "http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        TrelloBadgesDTO trelloBadgesDTO = new TrelloBadgesDTO();

        CreatedTrelloCardDTO createdTrelloCardDTO = new CreatedTrelloCardDTO(
                "1",
                "Test task",
                "http://test.com",
                trelloBadgesDTO
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDTO.class)).thenReturn(createdTrelloCardDTO);
        //when
        CreatedTrelloCardDTO newCard = trelloClient.createNewCard(trelloCardDTO);
        //then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    void shouldReturnEmptyList() throws URISyntaxException {
        //given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com/");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getUsername()).thenReturn("test");

        URI uri = new URI("https://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDTO[].class)).thenReturn(null);
        //when
        List<TrelloBoardDTO> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //then
        assertEquals(0, fetchedTrelloBoards.size());
    }
}