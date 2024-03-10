package com.maciejfranikowski.magicTheGatheringCardOrganizer.repository;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.LoanCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanCardDao extends JpaRepository<LoanCard, Integer> {
}
