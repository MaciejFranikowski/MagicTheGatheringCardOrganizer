package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

public interface Card {
    CardBox getBox();
    void setBox(CardBox box);
    String getName();
    void setName(String name);
    int getId();
    void setId(int id);
}
