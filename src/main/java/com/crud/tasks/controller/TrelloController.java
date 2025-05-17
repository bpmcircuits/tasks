package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.domain.TrelloCardDTO;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
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
    public void getTrelloBoards() {
        List<TrelloBoardDTO> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards
                .stream()
                .filter(b -> b.getId() != null && b.getName() != null)
                .filter(b -> b.getName().contains(KODILLA))
                .forEach(trelloBoardDTO -> {
            System.out.println(trelloBoardDTO.getId() + " " + trelloBoardDTO.getName());
            System.out.println("This board has the following lists: ");
            trelloBoardDTO.getLists().forEach(list -> {
                System.out.println(list.getName() + " - " + list.getId() + " - " + list.isClosed());
            });
        });
    }

    @PostMapping("cards")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDTO trelloCardDTO) {
        return trelloClient.createNewCard(trelloCardDTO);
    }

}
