package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDTO;
import com.crud.tasks.domain.TrelloCardDTO;
import com.crud.tasks.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);
    private final RestTemplate restTemplate;
    private final TrelloConfig trelloConfig;

    public List<TrelloBoardDTO> getTrelloBoards() {

        URI url = getUri();

        try {
            TrelloBoardDTO[] boardResponses = restTemplate.getForObject(url, TrelloBoardDTO[].class);
            return Optional.ofNullable(boardResponses).map(Arrays::asList).orElse(Collections.emptyList())
                    .stream()
                    .filter(b -> Objects.nonNull(b.getId()) && Objects.nonNull(b.getName()))
                    .filter(b -> b.getName().toLowerCase().contains("kodilla"))
                    .toList();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public CreatedTrelloCard createNewCard(TrelloCardDTO trelloCardDTO) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardDTO.getName())
                .queryParam("desc", trelloCardDTO.getDescription())
                .queryParam("pos", trelloCardDTO.getPos())
                .queryParam("idList", trelloCardDTO.getListId())
                .build().encode().toUri();
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }

    @NotNull
    private URI getUri() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getUsername() + "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }


}
