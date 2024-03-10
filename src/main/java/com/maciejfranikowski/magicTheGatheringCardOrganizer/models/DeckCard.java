package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;

@Entity()
@Table(name = "deck_card")
public class DeckCard implements Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "box_id")
    @ManyToOne()
    @JoinColumn(name = "box_id")
    private CardBox box;
    @Column(name = "name")
    private String name;
    @Column(name = "deck_name")
    private String deckName;
    public DeckCard(){}
    public DeckCard(CardBox box, String name){
        this.box = box;
        this.name = name;
    }
    public DeckCard(CardBox box, String name, String deckName){
        this.box = box;
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
    public CardBox getBox() {
        return box;
    }

    @Override
    public void setBox(CardBox box) {
        this.box = box;
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
