package com.maciejfranikowski.magicTheGatheringCardOrganizer.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;


@Setter
@Getter
@ToString
public class ApiCard {
    @JsonProperty("name")
    private String name;
    @JsonProperty("image_uris")
    private HashMap<String, String> imageUris;
    @JsonProperty("mana_cost")
    private String manaCost;
    @JsonProperty("type_line")
    private String type;
    @JsonProperty("oracle_text")
    private String oracleText;
    @JsonProperty("artist")
    private String artist;
    @JsonProperty("flavor_text")
    private String flavorText;
    @JsonProperty("legalities")
    private HashMap<String, String> legalities;
}
