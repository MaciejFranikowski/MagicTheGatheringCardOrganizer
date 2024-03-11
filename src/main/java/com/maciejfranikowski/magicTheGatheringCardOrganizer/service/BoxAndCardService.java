package com.maciejfranikowski.magicTheGatheringCardOrganizer.service;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CardBoxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoxAndCardService {
    private final CardBoxDao cardBoxDao;
    public BoxAndCardService(@Autowired CardBoxDao cardBoxDao){
        this.cardBoxDao =  cardBoxDao;
    }

    public Iterable<CardBox> getCardBoxes(){return cardBoxDao.findAll();}
}
