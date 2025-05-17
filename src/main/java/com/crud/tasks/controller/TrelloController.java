package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.domain.TrelloCardDTO;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
@CrossOrigin
public class TrelloController {

    private final TrelloClient trelloClient;

    private static final String KODILLA = "Kodilla";

    @GetMapping("boards")
    public ResponseEntity<List<TrelloBoardDTO>> getTrelloBoards() {
        return ResponseEntity.ok(trelloClient.getTrelloBoards());
    }

    @PostMapping("cards")
    public ResponseEntity<CreatedTrelloCard> createTrelloCard(@RequestBody TrelloCardDTO trelloCardDTO) {
        return ResponseEntity.ok(trelloClient.createNewCard(trelloCardDTO));
    }

}
