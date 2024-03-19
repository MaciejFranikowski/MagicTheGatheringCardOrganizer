package com.maciejfranikowski.magicTheGatheringCardOrganizer.repository;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.LoanCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanCardDao extends JpaRepository<LoanCard, Integer> {
    Iterable<LoanCard> findByBox(CardBox box);
    Optional<LoanCard> findByName(String name);
}
