package com.crud.tasks.domain;

import lombok.Data;

@Data
public class TrelloCardDTO {

    private String name;
    private String description;
    private String pos;
    private String listId;

}
