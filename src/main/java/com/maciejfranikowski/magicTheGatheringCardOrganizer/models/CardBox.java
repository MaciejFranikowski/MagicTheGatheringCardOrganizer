package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "box")
public class CardBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "color")
    private String color;
    @OneToMany(mappedBy = "boxId")
    private List<CollectionCard> collectionCards;
    @OneToMany(mappedBy = "boxId")
    private List<DeckCard> deckCards;
    @OneToMany(mappedBy = "boxId")
    private List<LoanCard> loanCards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<CollectionCard> getCollectionCards() {
        return collectionCards;
    }

    public void setCollectionCards(List<CollectionCard> collectionCards) {
        this.collectionCards = collectionCards;
    }

    public List<DeckCard> getDeckCards() {
        return deckCards;
    }

    public void setDeckCards(List<DeckCard> deckCards) {
        this.deckCards = deckCards;
    }

    public List<LoanCard> getLoanCards() {
        return loanCards;
    }

    public void setLoanCards(List<LoanCard> loanCards) {
        this.loanCards = loanCards;
    }
}
