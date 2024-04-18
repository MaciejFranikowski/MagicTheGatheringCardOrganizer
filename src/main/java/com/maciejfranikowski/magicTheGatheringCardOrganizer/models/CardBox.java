package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "box")
@Getter @Setter @NoArgsConstructor
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
    @OneToMany(mappedBy = "box", cascade = CascadeType.REMOVE)
    private List<CollectionCard> collectionCards;
    @OneToMany(mappedBy = "box", cascade = CascadeType.REMOVE)
    private List<DeckCard> deckCards;
    @OneToMany(mappedBy = "box", cascade = CascadeType.REMOVE)
    private List<LoanCard> loanCards;

    public CardBox(String name, String location, String color) {
        this.name = name;
        this.location = location;
        this.color = color;
    }

    @Override
    public String toString() {
        return "CardBox{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
