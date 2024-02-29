package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;

@Entity
@Table(name = "collection_card")
public class CollectionCard implements Card{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "box_id")
    private int boxId;
    @Column(name = "name")
    private String name;
    @Column(name = "set_name")
    private String setName;
    public CollectionCard(){}
    public CollectionCard(int boxId, String name){
        this.boxId = boxId;
        this.name = name;
    }
    public CollectionCard(int boxId, String name, String setName){
        this.boxId = boxId;
        this.name = name;
        this.setName = setName;
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

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }
}
