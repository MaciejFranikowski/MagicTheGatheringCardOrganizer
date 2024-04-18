package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "loan_card")
@Getter @Setter @NoArgsConstructor @ToString
public class LoanCard implements Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne()
    @JoinColumn(name = "box_id")
    private CardBox box;
    @Column(name = "name")
    private String name;
    @Column(name = "owner_firstname")
    private String ownerFirstName;
    @Column(name = "owner_lastname")
    private String ownerLastName;

    public LoanCard(CardBox box, String name, String ownerFirstName, String ownerLastName){
        this.box = box;
        this.name = name;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
    }
}
