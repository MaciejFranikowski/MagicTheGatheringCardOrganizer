package com.maciejfranikowski.magicTheGatheringCardOrganizer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getImageUris() {
        return imageUris;
    }

    public void setImageUris(HashMap<String, String> imageUris) {
        this.imageUris = imageUris;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOracleText() {
        return oracleText;
    }

    public void setOracleText(String oracleText) {
        this.oracleText = oracleText;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    public HashMap<String, String> getLegalities() {
        return legalities;
    }

    public void setLegalities(HashMap<String, String> legalities) {
        this.legalities = legalities;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    @Override
    public String toString() {
        return "ApiCard{" +
                "name='" + name + '\'' +
                ", imageUris=" + imageUris +
                ", manaCost='" + manaCost + '\'' +
                ", type='" + type + '\'' +
                ", oracleText='" + oracleText + '\'' +
                ", artist='" + artist + '\'' +
                ", flavorText='" + flavorText + '\'' +
                ", legalities=" + legalities +
                '}';
    }
}
