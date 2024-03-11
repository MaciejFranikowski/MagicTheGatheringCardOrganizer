package com.maciejfranikowski.magicTheGatheringCardOrganizer.repository;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CollectionCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionCardDao extends JpaRepository<CollectionCard, Integer> {
}
