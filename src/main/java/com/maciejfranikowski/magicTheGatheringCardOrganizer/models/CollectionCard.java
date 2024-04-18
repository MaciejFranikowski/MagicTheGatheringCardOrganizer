package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "collection_card")
@Getter @Setter @ToString @NoArgsConstructor
public class CollectionCard implements Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne()
    @JoinColumn(name = "box_id")
    private CardBox box;
    @Column(name = "name")
    private String name;
    @Column(name = "set_name")
    private String setName;

    public CollectionCard(CardBox box, String name, String setName){
        this.box = box;
        this.name = name;
        this.setName = setName;
    }
}
