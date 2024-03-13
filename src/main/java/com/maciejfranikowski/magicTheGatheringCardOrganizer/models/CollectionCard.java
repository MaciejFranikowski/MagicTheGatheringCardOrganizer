package com.maciejfranikowski.magicTheGatheringCardOrganizer.models;

import jakarta.persistence.*;

@Entity
@Table(name = "collection_card")
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
    public CollectionCard(){}
    public CollectionCard(CardBox box, String name, String setName){
        this.box = box;
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

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    @Override
    public String toString() {
        return "CollectionCard{" +
                "id=" + id +
                ", box=" + box +
                ", name='" + name + '\'' +
                ", setName='" + setName + '\'' +
                '}';
    }
}
