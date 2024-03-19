package com.maciejfranikowski.magicTheGatheringCardOrganizer.service;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.DeckCard;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CardBoxDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.DeckCardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BoxAndCardService {
    private final CardBoxDao cardBoxDao;
    private final DeckCardDao deckCardDao;
    public BoxAndCardService(@Autowired CardBoxDao cardBoxDao, @Autowired DeckCardDao deckCardDao){
        this.cardBoxDao =  cardBoxDao;
        this.deckCardDao = deckCardDao;
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
    public void deleteCardBox(int cardBoxId){
        if(!checkIfCardBoxIsNull(cardBoxId))
            cardBoxDao.deleteById(cardBoxId);
    }

    public boolean createDeckCard(int boxId, String name, String deckName){
        if(checkIfCardBoxIsNull(boxId)){
            return false;
        }
        DeckCard deckCard = new DeckCard(cardBoxDao.findById(boxId).get(), name, deckName);
        deckCard.setId(0);
        deckCardDao.save(deckCard);
        return true;
    }
}
