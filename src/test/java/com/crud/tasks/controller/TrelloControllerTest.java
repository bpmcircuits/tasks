package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDTO;
import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.domain.TrelloCardDTO;
import com.crud.tasks.domain.TrelloListDTO;
import com.crud.tasks.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception{
        // Given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(Collections.emptyList());

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception{
        // Given
        List<TrelloListDTO> trelloLists = List.of(new TrelloListDTO("2", "test_list", false));
        List<TrelloBoardDTO> trelloBoards = List.of(new TrelloBoardDTO("1", "test_board", trelloLists));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                    .get("/v1/trello/boards")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                // Trello board fields
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("test_board")))
                // Trello list fields
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id", Matchers.is("2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name", Matchers.is("test_list")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed", Matchers.is(false)));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        // Given
        TrelloCardDTO trelloCardDTO =
                new TrelloCardDTO("Test", "Test Desc", "top", "1");

        CreatedTrelloCardDTO createdTrelloCardDTO =
                new CreatedTrelloCardDTO("232", "Test", "http://test.com", null);

        when(trelloFacade.createCard(any(TrelloCardDTO.class))).thenReturn(createdTrelloCardDTO);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDTO);

        // When Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("232")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl", Matchers.is("http://test.com")));
    }
}