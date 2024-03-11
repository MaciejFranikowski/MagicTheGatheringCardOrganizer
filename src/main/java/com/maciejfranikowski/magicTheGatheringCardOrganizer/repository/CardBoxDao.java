package com.maciejfranikowski.magicTheGatheringCardOrganizer.repository;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CardBoxDao extends JpaRepository<CardBox, Integer> {
    public Optional<CardBox> findByName(String name);
}
