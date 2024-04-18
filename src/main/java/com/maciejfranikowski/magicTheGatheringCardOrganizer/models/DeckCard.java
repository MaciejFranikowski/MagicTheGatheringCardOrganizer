package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity()
@Table(name = "deck_card")
@Getter @Setter @NoArgsConstructor @ToString
public class DeckCard implements Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne()
    @JoinColumn(name = "box_id")
    private CardBox box;
    @Column(name = "name")
    private String name;
    @Column(name = "deck_name")
    private String deckName;

    public DeckCard(CardBox box, String name, String deckName){
        this.box = box;
        this.name = name;
        this.deckName = deckName;
    }

}
