package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrelloCardDTO {

    private String name;
    private String description;
    private String pos;
    private String listId;

}
