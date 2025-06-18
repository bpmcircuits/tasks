package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TrelloBoard {
    private String id;
    private String name;
    private List<TrelloList> lists;
}
