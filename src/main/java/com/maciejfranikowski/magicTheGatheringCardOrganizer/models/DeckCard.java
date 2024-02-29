package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;

@Entity()
@Table(name = "deck_card")
public class DeckCard implements Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "box_id")
    private int boxId;
    @Column(name = "name")
    private String name;
    @Column(name = "deck_name")
    private String deckName;
    public DeckCard(){}
    public DeckCard(int boxId, String name){
        this.boxId = boxId;
        this.name = name;
    }
    public DeckCard(int boxId, String name, String deckName){
        this.boxId = boxId;
        this.name = name;
        this.deckName = deckName;
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getBoxId() {
        return boxId;
    }

    @Override
    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
