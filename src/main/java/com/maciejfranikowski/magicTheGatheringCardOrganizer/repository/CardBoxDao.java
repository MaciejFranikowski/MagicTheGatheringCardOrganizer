package com.maciejfranikowski.magicTheGatheringCardOrganizer.repository;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardBoxDao extends JpaRepository<CardBox, Integer> {
}
