package com.maciejfranikowski.magicTheGatheringCardOrganizer;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.*;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CardBoxDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CollectionCardDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.DeckCardDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.LoanCardDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.BoxAndCardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class BoxAndCardServiceTest {
    @Value("${sql.scripts.create.box}")
    private String createBoxSql;
    @Value("${sql.scripts.delete.box}")
    private String deleteBoxSql;
    @Value("${sql.scripts.delete.deck_cards}")
    private String deleteDeckCardsSql;
    @Value("${sql.scripts.delete.collection_cards}")
    private String deleteCollectionCardsSql;
    @Value("${sql.scripts.delete.loan_cards}")
    private String deleteLoanCardsSql;


    @Autowired
    private BoxAndCardService boxAndCardService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CardBoxDao cardBoxDao;
    @Autowired
    private DeckCardDao deckCardDao;
    @Autowired
    private CollectionCardDao collectionCardDao;
    @Autowired
    private LoanCardDao loanCardDao;

    @BeforeEach
    public void beforeEachSql(){
        jdbcTemplate.execute(createBoxSql);
    }
    @AfterEach
    public void cleanUpDatabase(){
        jdbcTemplate.execute(deleteDeckCardsSql);
        jdbcTemplate.execute(deleteLoanCardsSql);
        jdbcTemplate.execute(deleteCollectionCardsSql);
        jdbcTemplate.execute(deleteBoxSql);
    }

    @Test
    @Sql("/sql/createBoxes.sql")
    public void getCardBoxes(){
        Iterable<CardBox> cardBoxList = boxAndCardService.getCardBoxes();
        List<CardBox> cardBoxes = new ArrayList<>();
        for (CardBox box : cardBoxList){
            cardBoxes.add(box);
        }
        assertEquals(4, cardBoxes.size(), "There should 4 boxes");
    }
    @Test
    public void createCardBox(){
        String name = "new card box";
        String location = "on the ceiling";
        String color = "black";
        boxAndCardService.createCardBox(name, location, color);
        Optional<CardBox> foundCarBox = cardBoxDao.findByName(name);
        assertTrue(foundCarBox.isPresent(), "couldn't box find by name");
        assertEquals(location, foundCarBox.get().getLocation(), "wrong location");
        assertEquals(color, foundCarBox.get().getColor(), "wrong color");
        Iterable<CardBox> cardBoxList = boxAndCardService.getCardBoxes();
        List<CardBox> cardBoxes = new ArrayList<>();
        for (CardBox box : cardBoxList){
            cardBoxes.add(box);
        }
        assertEquals(2, cardBoxes.size(), "There should 2 boxes");
    }
    @Test
    public void checkIfCardBoxIsNull(){
        assertTrue(boxAndCardService.checkIfCardBoxIsNull(2));
        assertFalse(boxAndCardService.checkIfCardBoxIsNull(99));
    }
    @Test
    @Sql("/sql/getCardBoxInformation.sql")
    public void getCardBoxInformation(){
        int cardBoxId = 2;
        CardBox cardBox = boxAndCardService.getCardBoxInformation(cardBoxId);
        assertEquals("second box", cardBox.getName());
        assertEquals("black", cardBox.getColor());
        assertNotNull(cardBox.getDeckCards());
        assertEquals(2, cardBox.getDeckCards().size());
        assertNotNull(cardBox.getCollectionCards());
        assertEquals(1, cardBox.getCollectionCards().size());
        assertEquals(0,cardBox.getLoanCards().size());
    }
    @Test
    @Sql("/sql/deleteCardBox.sql")
    public void deleteCardBox(){
        int cardBoxId = 2;
        Optional<CardBox> cardBoxOptional = cardBoxDao.findById(cardBoxId);
        Optional<DeckCard> deckCardOptional = deckCardDao.findByName("Force of will");
        Optional<LoanCard> loanCardOptional = loanCardDao.findByName("Daze");
        Optional<CollectionCard> collectionCardOptional = collectionCardDao.findByName("Plains");
        assertTrue(cardBoxOptional.isPresent());
        assertTrue(deckCardOptional.isPresent());
        assertTrue(collectionCardOptional.isPresent());
        assertTrue(loanCardOptional.isPresent());
        boxAndCardService.deleteCardBox(cardBoxId);
        cardBoxOptional = cardBoxDao.findById(cardBoxId);
        deckCardOptional = deckCardDao.findById(1);
        loanCardOptional = loanCardDao.findById(1);
        collectionCardOptional = collectionCardDao.findById(1);
        assertFalse(cardBoxOptional.isPresent());
        assertFalse(deckCardOptional.isPresent());
        assertFalse(collectionCardOptional.isPresent());
        assertFalse(loanCardOptional.isPresent());
    }
    @Test
    public void createDeckCard(){
        Optional<CardBox> cardBox = cardBoxDao.findById(99);
        assertTrue(cardBox.isPresent());
        assertTrue(boxAndCardService.createDeckCard(99, "Force of Will", "SultaiBeans"));
        Iterable<DeckCard> deckCards = deckCardDao.findByBox(cardBox.get());
        assertEquals(((Collection<DeckCard>)deckCards).size(), 1);
    }
    @Test
    public void createDeckCardWrongBox(){
        Optional<CardBox> cardBox = cardBoxDao.findById(100);
        assertTrue(cardBox.isEmpty());
        CardBox fakeCardBox = new CardBox("not saved box", "hidden", "black");
        fakeCardBox.setId(100);
        assertFalse(boxAndCardService.createDeckCard(100, "Force of Will", "SultaiBeans"));
        Iterable<DeckCard> deckCards = deckCardDao.findByBox(fakeCardBox);
        assertEquals(((Collection<DeckCard>)deckCards).size(), 0);
    }
    @Test
    public void createLoanCard(){
        Optional<CardBox> cardBox = cardBoxDao.findById(99);
        assertTrue(cardBox.isPresent());
        assertTrue(boxAndCardService.createLoanCard(99, "Force of Will", "Maciej", "Franikowski"));
        Iterable<LoanCard> loanCards = loanCardDao.findByBox(cardBox.get());
        assertEquals(((Collection<LoanCard>)loanCards).size(), 1);
    }
    @Test
    public void createLoanCardWrongBox(){
        Optional<CardBox> cardBox = cardBoxDao.findById(100);
        assertTrue(cardBox.isEmpty());
        CardBox fakeCardBox = new CardBox("not saved box", "hidden", "black");
        fakeCardBox.setId(100);
        assertFalse(boxAndCardService.createLoanCard(100, "Force of Will", "Maciej", "Franikowski"));
        Iterable<LoanCard> loanCards = loanCardDao.findByBox(fakeCardBox);
        assertEquals(((Collection<LoanCard>)loanCards).size(), 0);
    }
    @Test
    public void createCollectionCard(){
        Optional<CardBox> cardBox = cardBoxDao.findById(99);
        assertTrue(cardBox.isPresent());
        assertTrue(boxAndCardService.createCollectionCard(99, "Force of Will", "Nemesis"));
        Iterable<CollectionCard> collectionCards = collectionCardDao.findByBox(cardBox.get());
        assertEquals(((Collection<CollectionCard>)collectionCards).size(), 1);
    }
    @Test
    public void createCollectionCardWrongBox(){
        Optional<CardBox> cardBox = cardBoxDao.findById(100);
        assertTrue(cardBox.isEmpty());
        CardBox fakeCardBox = new CardBox("not saved box", "hidden", "black");
        fakeCardBox.setId(100);
        assertFalse(boxAndCardService.createCollectionCard(100, "Force of Will", "Nemesis"));
        Iterable<CollectionCard> collectionCards = collectionCardDao.findByBox(fakeCardBox);
        assertEquals(((Collection<CollectionCard>)collectionCards).size(), 0);
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteDeckCard(){
        List<DeckCard> deckCards = deckCardDao.findAll();
        assertEquals(deckCards.size(), 1);
        assertTrue(boxAndCardService.deleteCard(66,"deck"));
        deckCards = deckCardDao.findAll();
        assertEquals(deckCards.size(), 0);
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteDeckCard_NotFound(){
        List<DeckCard> deckCards = deckCardDao.findAll();
        assertEquals(deckCards.size(), 1);
        assertFalse(boxAndCardService.deleteCard(67,"deck"));
        deckCards = deckCardDao.findAll();
        assertEquals(deckCards.size(), 1);
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteLoanCard(){
        List<LoanCard> loanCards = loanCardDao.findAll();
        assertEquals(loanCards.size(), 1);
        assertTrue(boxAndCardService.deleteCard(66,"loan"));
        loanCards = loanCardDao.findAll();
        assertEquals(loanCards.size(), 0);
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteLoanCard_NotFound(){
        List<LoanCard> loanCards = loanCardDao.findAll();
        assertEquals(loanCards.size(), 1);
        assertFalse(boxAndCardService.deleteCard(67,"loan"));
        loanCards = loanCardDao.findAll();
        assertEquals(loanCards.size(), 1);
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteCollectionCard(){
        List<CollectionCard> collectionCards = collectionCardDao.findAll();
        assertEquals(collectionCards.size(), 1);
        assertTrue(boxAndCardService.deleteCard(66,"collection"));
        collectionCards = collectionCardDao.findAll();
        assertEquals(collectionCards.size(), 0);
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteCollectionCard_NotFound(){
        List<CollectionCard> collectionCards = collectionCardDao.findAll();
        assertEquals(collectionCards.size(), 1);
        assertFalse(boxAndCardService.deleteCard(67,"collection"));
        collectionCards = collectionCardDao.findAll();
        assertEquals(collectionCards.size(), 1);
    }

}
