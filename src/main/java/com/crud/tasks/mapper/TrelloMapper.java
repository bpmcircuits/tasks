package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloMapper {

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDTO> boards) {
        return boards.stream()
                .map(trelloBoardDTO -> new TrelloBoard(trelloBoardDTO.getId(),
                        trelloBoardDTO.getName(),
                        mapToList(trelloBoardDTO.getLists())))
                .toList();
    }

    public List<TrelloBoardDTO> mapToBoardsDTO(final List<TrelloBoard> boards) {
        return boards.stream()
                .map(trelloBoard ->
                        new TrelloBoardDTO(trelloBoard.getId(),
                                trelloBoard.getName(),
                                mapToListDTO(trelloBoard.getLists())))
                .toList();
    }

    public List<TrelloList> mapToList(List<TrelloListDTO> lists) {
        return lists.stream().
                map(trelloListDTO -> new TrelloList(trelloListDTO.getId(),
                        trelloListDTO.getName(),
                        trelloListDTO.isClosed()))
                .toList();
    }

    private List<TrelloListDTO> mapToListDTO(List<TrelloList> lists) {
        return lists.stream()
                .map(trelloList -> new TrelloListDTO(trelloList.getId(),
                        trelloList.getName(),
                        trelloList.isClosed()))
                .toList();
    }

    public TrelloCardDTO mapToCardDTO(final TrelloCard trelloCard) {
        return new TrelloCardDTO(trelloCard.getName(),
                trelloCard.getDescription(),
                trelloCard.getPos(),
                trelloCard.getListId());
    }

    public TrelloCard mapToCard(final TrelloCardDTO trelloCardDTO) {
        return new TrelloCard(trelloCardDTO.getName(),
                trelloCardDTO.getDescription(),
                trelloCardDTO.getPos(),
                trelloCardDTO.getListId());
    }
}
