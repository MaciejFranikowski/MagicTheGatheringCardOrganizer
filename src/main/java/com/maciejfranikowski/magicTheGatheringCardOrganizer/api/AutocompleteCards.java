package com.maciejfranikowski.magicTheGatheringCardOrganizer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AutocompleteCards {
    @JsonProperty("object")
    private String object;

    @JsonProperty("total_values")
    private int totalValues;

    @JsonProperty("data")
    private List<String> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getTotalValues() {
        return totalValues;
    }

    public void setTotalValues(int totalValues) {
        this.totalValues = totalValues;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AutocompleteCards{" +
                "object='" + object + '\'' +
                ", totalValues=" + totalValues +
                ", data=" + data +
                '}';
    }
}
