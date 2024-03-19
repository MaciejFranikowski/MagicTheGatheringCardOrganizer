package com.maciejfranikowski.magicTheGatheringCardOrganizer.repository;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CollectionCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectionCardDao extends JpaRepository<CollectionCard, Integer> {
    Iterable<CollectionCard> findByBox(CardBox box);
    Optional<CollectionCard> findByName(String name);
}
