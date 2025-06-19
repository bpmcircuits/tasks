package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void testFetchTrelloBoards() {
        // Given
        List<TrelloListDTO> trelloListDTOs = List.of(
                new TrelloListDTO("1", "test_list", false)
        );

        List<TrelloBoardDTO> trelloBoardDTOs = List.of(
                new TrelloBoardDTO("1", "test_board", trelloListDTOs)
        );

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDTOs);

        // When
        List<TrelloBoardDTO> result = trelloService.fetchTrelloBoards();

        // Then
        assertNotNull(result);
        assertEquals(trelloBoardDTOs.size(), result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("test_board", result.get(0).getName());
    }

    @Test
    void testFetchEmptyBoards() {
        // Given
        when(trelloService.fetchTrelloBoards()).thenReturn(List.of());

        // When
        List<TrelloBoardDTO> result = trelloService.fetchTrelloBoards();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testCreateTrelloCard() {
        // Given
        TrelloCardDTO trelloCardDTO =
               new TrelloCardDTO("test_name", "desc", "pos1", "2");

        CreatedTrelloCardDTO createdTrelloCardDTO =
                new CreatedTrelloCardDTO("1", "test_name_trello", "url", null);

        when(trelloService.createdTrelloCard(trelloCardDTO)).thenReturn(createdTrelloCardDTO);
        when(adminConfig.getAdminMail()).thenReturn("test@email.com");

        // When
        CreatedTrelloCardDTO result = trelloService.createdTrelloCard(trelloCardDTO);

        assertNotNull(result);
        assertEquals(createdTrelloCardDTO.getId(), result.getId());
        assertEquals(createdTrelloCardDTO.getName(), result.getName());
        verify(trelloClient, times(1)).createNewCard(trelloCardDTO);
        verify(emailService, times(1)).send(any(Mail.class));
    }
}
