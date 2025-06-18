package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDTO;
import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.domain.TrelloCardDTO;
import com.crud.tasks.facade.TrelloFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
@CrossOrigin
public class TrelloController {

    private final TrelloFacade trelloFacade;

    @GetMapping("boards")
    public ResponseEntity<List<TrelloBoardDTO>> getTrelloBoards() {
        return ResponseEntity.ok(trelloFacade.fetchTrelloBoards());
    }

    @PostMapping("cards")
    public ResponseEntity<CreatedTrelloCardDTO> createTrelloCard(@RequestBody TrelloCardDTO trelloCardDTO) {
        return ResponseEntity.ok(trelloFacade.createCard(trelloCardDTO));
    }

}
