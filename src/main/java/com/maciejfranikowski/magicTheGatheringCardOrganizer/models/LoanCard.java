package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_card")
public class LoanCard implements Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "box_id")
    private int boxId;
    @Column(name = "name")
    private String name;
    @Column(name = "owner_firstname")
    private String ownerFirstName;
    @Column(name = "owner_lastname")
    private String ownerLastName;
    public LoanCard(){}
    public LoanCard(int boxId, String name){
        this.boxId = boxId;
        this.name = name;
    }
    public LoanCard(int boxId, String name, String ownerFirstName, String ownerLastName){
        this.boxId = boxId;
        this.name = name;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
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

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }
}
