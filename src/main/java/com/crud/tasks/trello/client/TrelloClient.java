package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.domain.TrelloCardDTO;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String username;

    public List<TrelloBoardDTO> getTrelloBoards() {

        URI url = getUri();
        TrelloBoardDTO[] boardResponses = restTemplate.getForObject(url, TrelloBoardDTO[].class);

        return Optional.ofNullable(boardResponses).map(Arrays::asList).orElse(Collections.emptyList());
    }

    public CreatedTrelloCard createNewCard(TrelloCardDTO trelloCardDTO) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDTO.getName())
                .queryParam("desc", trelloCardDTO.getDescription())
                .queryParam("pos", trelloCardDTO.getPos())
                .queryParam("idList", trelloCardDTO.getListId())
                .build().encode().toUri();
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }

    @NotNull
    private URI getUri() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }


}
