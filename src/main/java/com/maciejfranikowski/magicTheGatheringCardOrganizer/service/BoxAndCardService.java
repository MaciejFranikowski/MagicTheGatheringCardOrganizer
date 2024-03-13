package com.maciejfranikowski.magicTheGatheringCardOrganizer.service;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CardBoxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BoxAndCardService {
    private final CardBoxDao cardBoxDao;
    public BoxAndCardService(@Autowired CardBoxDao cardBoxDao){
        this.cardBoxDao =  cardBoxDao;
    }

    public Iterable<CardBox> getCardBoxes(){return cardBoxDao.findAll();}
    public void createCardBox(String name, String location, String color){
        CardBox cardBox = new CardBox(name, location,color);
        cardBox.setId(0);
        cardBoxDao.save(cardBox);
    }
    public CardBox getCardBoxInformation(int cardBoxId){
        Optional<CardBox> cardBoxOptional = cardBoxDao.findById(cardBoxId);
        if(cardBoxOptional.isPresent()){
            CardBox cardBox = cardBoxOptional.get();
            cardBox.getDeckCards().size();
            cardBox.getLoanCards().size();
            cardBox.getCollectionCards().size();
            return cardBox;
        }
        return null;
    }
    public boolean checkIfCardBoxIsNull(int cardBoxId){
        return cardBoxDao.findById(cardBoxId).isEmpty();
    }
}
