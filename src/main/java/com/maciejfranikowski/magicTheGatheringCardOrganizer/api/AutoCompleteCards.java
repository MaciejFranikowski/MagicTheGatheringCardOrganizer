package com.maciejfranikowski.magicTheGatheringCardOrganizer.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class AutoCompleteCards {
    @JsonProperty("object")
    private String object;
    @JsonProperty("total_values")
    private int totalValues;
    @JsonProperty("data")
    private List<String> data;

}
