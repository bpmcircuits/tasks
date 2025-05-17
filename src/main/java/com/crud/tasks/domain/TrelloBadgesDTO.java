package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class TrelloBadgesDTO {

    @JsonProperty("votes")
    private int votes;
    @JsonProperty("attachmentsByType")
    private TrelloAttachmentsByTypeDTO attachmentsByType;
}
