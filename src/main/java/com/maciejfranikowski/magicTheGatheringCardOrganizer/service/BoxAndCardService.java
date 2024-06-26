package com.maciejfranikowski.magicTheGatheringCardOrganizer.service;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CollectionCard;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.DeckCard;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.LoanCard;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CardBoxDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CollectionCardDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.DeckCardDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.LoanCardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BoxAndCardService {
    private final CardBoxDao cardBoxDao;
    private final DeckCardDao deckCardDao;
    private final LoanCardDao loanCardDao;
    private final CollectionCardDao collectionCardDao;

    public BoxAndCardService(@Autowired CardBoxDao cardBoxDao,
                             @Autowired DeckCardDao deckCardDao,
                             @Autowired LoanCardDao loanCardDao,
                             @Autowired CollectionCardDao collectionCardDao) {
        this.cardBoxDao = cardBoxDao;
        this.deckCardDao = deckCardDao;
        this.loanCardDao = loanCardDao;
        this.collectionCardDao = collectionCardDao;
    }

    public Iterable<CardBox> getCardBoxes() {
        return cardBoxDao.findAll();
    }

    public void createCardBox(String name, String location, String color) {
        CardBox cardBox = new CardBox(name, location, color);
        cardBox.setId(0);
        cardBoxDao.save(cardBox);
    }

    public CardBox getCardBoxInformation(int cardBoxId) {
        Optional<CardBox> cardBoxOptional = cardBoxDao.findById(cardBoxId);
        if (cardBoxOptional.isPresent()) {
            CardBox cardBox = cardBoxOptional.get();
            cardBox.getDeckCards().size();
            cardBox.getLoanCards().size();
            cardBox.getCollectionCards().size();
            return cardBox;
        }
        return null;
    }

    public boolean checkIfCardBoxIsNull(int cardBoxId) {
        return cardBoxDao.findById(cardBoxId).isEmpty();
    }

    public void deleteCardBox(int cardBoxId) {
        if (!checkIfCardBoxIsNull(cardBoxId))
            cardBoxDao.deleteById(cardBoxId);
    }

    public boolean createDeckCard(int boxId, String name, String deckName) {
        if (checkIfCardBoxIsNull(boxId)) {
            return false;
        }
        DeckCard deckCard = new DeckCard(cardBoxDao.findById(boxId).get(), name, deckName);
        deckCard.setId(0);
        deckCardDao.save(deckCard);
        return true;
    }

    public boolean createLoanCard(int boxId, String name, String ownerFirstName, String ownerLastName) {
        if (checkIfCardBoxIsNull(boxId)) {
            return false;
        }
        LoanCard loanCard = new LoanCard(cardBoxDao.findById(boxId).get(), name, ownerFirstName, ownerLastName);
        loanCard.setId(0);
        loanCardDao.save(loanCard);
        return true;
    }

    public boolean createCollectionCard(int boxId, String name, String setName) {
        if (checkIfCardBoxIsNull(boxId)) {
            return false;
        }
        CollectionCard collectionCard = new CollectionCard(cardBoxDao.findById(boxId).get(), name, setName);
        collectionCard.setId(0);
        collectionCardDao.save(collectionCard);
        return true;
    }
    public boolean checkIfCardIsNull(int id, String type) {
        return switch (type) {
            case "deck" -> deckCardDao.findById(id).isEmpty();
            case "loan" -> loanCardDao.findById(id).isEmpty();
            case "collection" -> collectionCardDao.findById(id).isEmpty();
            default -> true;
        };
    }

    public boolean deleteCard(int id, String type) {
        return switch (type) {
            case "deck" -> {
                if (!checkIfCardIsNull(id, type)) {
                    deckCardDao.deleteById(id);
                    yield true;
                }
                yield false;
            }
            case "loan" -> {
                if (!checkIfCardIsNull(id, type)) {
                    loanCardDao.deleteById(id);
                    yield true;
                }
                yield false;
            }
            case "collection" -> {
                if (!checkIfCardIsNull(id, type)) {
                    collectionCardDao.deleteById(id);
                    yield true;
                }
                yield false;
            }
            default -> false;
        };
    }
}
