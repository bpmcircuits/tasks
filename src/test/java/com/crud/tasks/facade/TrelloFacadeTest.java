package com.crud.tasks.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDTO;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchEmptyList() {
        // Given
        List<TrelloListDTO> trelloLists =
                List.of(new TrelloListDTO("1", "test_list", false));

        List<TrelloBoardDTO> trelloBoards =
                List.of(new TrelloBoardDTO("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDTO(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());

        // When
        List<TrelloBoardDTO> trelloBoardDTOs = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDTOs);
        assertTrue(trelloBoardDTOs.isEmpty());
    }

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDTO> trelloLists =
                List.of(new TrelloListDTO("1", "test_list", false));

        List<TrelloBoardDTO> trelloBoards =
                List.of(new TrelloBoardDTO("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDTO(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        // When
        List<TrelloBoardDTO> trelloBoardDTOs = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDTOs);
        assertEquals(1, trelloBoardDTOs.size());

        trelloBoardDTOs.forEach(trelloBoardDto -> {

            assertEquals("1", trelloBoardDto.getId());
            assertEquals("test", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("test_list", trelloListDto.getName());
                assertFalse(trelloListDto.isClosed());
            });
        });
    }

}