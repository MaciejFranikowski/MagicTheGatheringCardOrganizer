package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

public interface Card {
    int getBoxId();
    void setBoxId(int boxId);
    String getName();
    void setName(String name);
    int getId();
    void setId(int id);
}
