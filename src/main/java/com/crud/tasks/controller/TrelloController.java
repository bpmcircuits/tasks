package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        });
    }

}
