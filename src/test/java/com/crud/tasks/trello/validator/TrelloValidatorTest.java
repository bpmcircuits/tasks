package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TrelloValidatorTest {


    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    void testValidateCard() {
        // Given
        TrelloCard trelloCard =
                new TrelloCard("test_name", "desc", "pos", "1");

        // When Then
        trelloValidator.validateCard(trelloCard);
    }

    @Test
    void testValidateCardWithNull() {
        // Given
        TrelloCard trelloCard = null;

        // When Then
        assertThrows(NullPointerException.class, () -> trelloValidator.validateCard(trelloCard));
    }

    @Test
    void testValidateTrelloBoardsWithTestInBoardName() {
        // Given
        List<TrelloList> trelloList = List.of(
                new TrelloList("1", "test_list", false)
        );

        List<TrelloBoard> trelloBoardList = List.of(
                new TrelloBoard("1", "test", trelloList)
        );

        // When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(trelloBoardList);

        // Then
        assertEquals(0, result.size());
    }

    @Test
    void testValidateTrelloBoardsWithNoTestInBoardName() {
        // Given
        List<TrelloList> trelloList = List.of(
                new TrelloList("1", "test_list", false)
        );
        List<TrelloBoard> trelloBoardList = List.of(
                new TrelloBoard("1", "board_name", trelloList)
        );

        // When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(trelloBoardList);

        // Then
        assertEquals(1, result.size());
        assertEquals("board_name", result.get(0).getName());
    }

}