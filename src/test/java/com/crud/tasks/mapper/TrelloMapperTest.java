package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTest {

    private final TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    void testMapToBoards() {
        // Given
        List<TrelloBoardDTO> boardsDTO = List.of(
                new TrelloBoardDTO("1", "Board 1", List.of(new TrelloListDTO("1", "List 1", false))),
                new TrelloBoardDTO("2", "Board 2", List.of(new TrelloListDTO("2", "List 2", true)))
        );

        // When
        List<TrelloBoard> boards = trelloMapper.mapToBoards(boardsDTO);

        // Then
        assertNotNull(boards);
        assertEquals(2, boards.size());
        assertEquals("1", boards.get(0).getId());
        assertEquals("Board 1", boards.get(0).getName());

    }

    @Test
    void testMapToBoardsDTO() {
        // Given
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1", "Board 1", List.of(new TrelloList("1", "List 1", false))),
                new TrelloBoard("2", "Board 2", List.of(new TrelloList("2", "List 2", true)))
        );

        // When
        List<TrelloBoardDTO> boardsDTO = trelloMapper.mapToBoardsDTO(boards);

        // Then
        assertNotNull(boardsDTO);
        assertEquals(2, boardsDTO.size());
        assertEquals("1", boardsDTO.get(0).getId());
        assertEquals("Board 1", boardsDTO.get(0).getName());
    }

    @Test
    void testMapToList() {
        // Given
        List<TrelloListDTO> listsDTO = List.of(
                new TrelloListDTO("1", "List 1", false),
                new TrelloListDTO("2", "List 2", true)
        );

        // When
        List<TrelloList> lists = trelloMapper.mapToList(listsDTO);

        // Then
        assertNotNull(lists);
        assertEquals(2, lists.size());
        assertEquals("1", lists.get(0).getId());
        assertEquals("List 1", lists.get(0).getName());
    }

    @Test
    void testMapToCardDTO() {
        // Given
        TrelloCard card = new TrelloCard("Card 1", "Description 1", "top", "1");

        // When
        TrelloCardDTO cardDTO = trelloMapper.mapToCardDTO(card);

        // Then
        assertNotNull(cardDTO);
        assertEquals("Card 1", cardDTO.getName());
        assertEquals("Description 1", cardDTO.getDescription());
        assertEquals("top", cardDTO.getPos());
        assertEquals("1", cardDTO.getListId());
    }

    @Test
    void testMapToCard() {
        // Given
        TrelloCardDTO cardDTO = new TrelloCardDTO("Card 1", "Description 1", "top", "1");

        // When
        TrelloCard card = trelloMapper.mapToCard(cardDTO);

        // Then
        assertNotNull(card);
        assertEquals("Card 1", card.getName());
        assertEquals("Description 1", card.getDescription());
        assertEquals("top", card.getPos());
        assertEquals("1", card.getListId());
    }
}