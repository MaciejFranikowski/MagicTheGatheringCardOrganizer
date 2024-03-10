package com.maciejfranikowski.magicTheGatheringCardOrganizer.repository;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.DeckCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckCardDao extends JpaRepository<DeckCard, Integer> {
}
