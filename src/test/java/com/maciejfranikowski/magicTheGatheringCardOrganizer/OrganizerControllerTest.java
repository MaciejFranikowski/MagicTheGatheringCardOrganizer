package com.maciejfranikowski.magicTheGatheringCardOrganizer;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CollectionCard;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.DeckCard;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.LoanCard;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.BoxAndCardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class OrganizerControllerTest {
    @MockBean
    private BoxAndCardService mockBoxAndCardService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${sql.scripts.delete.box}")
    private String deleteBoxSql;
    @Value("${sql.scripts.create.box}")
    private String createBoxSql;
    @BeforeEach
    public void setUpDatabase(){
        jdbcTemplate.execute(createBoxSql);
    }
    @AfterEach
    public void cleanUpDatabase(){
        jdbcTemplate.execute(deleteBoxSql);
    }
    @Test
    public void getCardBoxesHttpRequest() throws Exception {
        List<CardBox> cardBoxList = new ArrayList<>();
        cardBoxList.add(new CardBox("first box","hidden","black"));
        cardBoxList.add(new CardBox("second box","hidden","white"));
        when(mockBoxAndCardService.getCardBoxes()).thenReturn(cardBoxList);
        assertIterableEquals(cardBoxList, mockBoxAndCardService.getCardBoxes());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"index");
        ModelAndViewAssert.assertModelAttributeAvailable(modelAndView, "boxes");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "boxes", cardBoxList);
    }
    @Test
    public void createCardBoxHttpRequest() throws Exception {
        String name = "added box";
        String location = "known";
        String color = "yellow";
        CardBox cardBox = new CardBox(name, location, color);
        List<CardBox> cardBoxList = new ArrayList<>(List.of(cardBox));
        when(mockBoxAndCardService.getCardBoxes()).thenReturn(cardBoxList);
        assertIterableEquals(mockBoxAndCardService.getCardBoxes(), cardBoxList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", name)
                .param("location", location)
                .param("color", color)
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"index");
        ModelAndViewAssert.assertModelAttributeAvailable(modelAndView, "boxes");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "boxes", cardBoxList);
    }
    @Test
    public void getCardBoxInformationHttpRequest() throws Exception {
        int cardBoxId = 1;
        String name = "added box";
        String location = "known";
        String color = "yellow";
        CardBox cardBox = new CardBox(name, location, color);
        LoanCard loanCard = new LoanCard(cardBox, "random card", "josh", "smith");
        cardBox.setLoanCards(new ArrayList<>(List.of(loanCard)));
        when(mockBoxAndCardService.getCardBoxInformation(cardBoxId)).thenReturn(cardBox);
        assertEquals(cardBox, mockBoxAndCardService.getCardBoxInformation(cardBoxId));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cardBox/{id}",cardBoxId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "cardBox");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView,"cardBox", cardBox);
    }
    @Test
    public void createDeckCardHttpRequest() throws Exception {
        String cardName = "Force of Will";
        String deckName = "Grixis Delver";
        int deckBoxId = 99;
        CardBox cardBox = new CardBox("test box", "hidden", "black");
        cardBox.setId(deckBoxId);
        DeckCard deckCard = new DeckCard(cardBox, cardName, deckName);
        cardBox.setDeckCards(new ArrayList<>(List.of(deckCard)));
        when(mockBoxAndCardService.getCardBoxInformation(99)).thenReturn(cardBox);
        when(mockBoxAndCardService.checkIfCardBoxIsNull(99)).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/create/card/deck")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name",cardName)
                .param("deckName", deckName)
                .param("boxId", Integer.toString(deckBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "cardBox");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView,"cardBox", cardBox);
    }
    @Test
    public void createLoanCardHttpRequest() throws Exception {
        String cardName = "Force of Will";
        String ownerFirstName = "John";
        String ownerLastName = "Smith";
        int deckBoxId = 99;
        CardBox cardBox = new CardBox("test box", "hidden", "black");
        cardBox.setId(deckBoxId);
        LoanCard loanCard = new LoanCard(cardBox, cardName, ownerFirstName, ownerLastName);
        cardBox.setLoanCards(new ArrayList<>(List.of(loanCard)));
        when(mockBoxAndCardService.getCardBoxInformation(99)).thenReturn(cardBox);
        when(mockBoxAndCardService.checkIfCardBoxIsNull(99)).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/create/card/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name",cardName)
                .param("ownerFirstName", ownerFirstName)
                .param("ownerLastName", ownerLastName)
                .param("boxId", Integer.toString(deckBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "cardBox");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView,"cardBox", cardBox);
    }
    @Test
    public void createCollectionCardHttpRequest() throws Exception {
        String cardName = "Force of Will";
        String setName = "Nemesis";
        int deckBoxId = 99;
        CardBox cardBox = new CardBox("test box", "hidden", "black");
        cardBox.setId(deckBoxId);
        CollectionCard collectionCard = new CollectionCard(cardBox, cardName, setName);
        cardBox.setCollectionCards(new ArrayList<>(List.of(collectionCard)));
        when(mockBoxAndCardService.getCardBoxInformation(99)).thenReturn(cardBox);
        when(mockBoxAndCardService.checkIfCardBoxIsNull(99)).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/create/card/collection")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name",cardName)
                .param("setName", setName)
                .param("boxId", Integer.toString(deckBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "cardBox");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView,"cardBox", cardBox);
    }
    @Test
    public void deleteDeckCard() throws Exception {
        int deckBoxId = 99;
        CardBox cardBox = new CardBox("test box", "hidden", "black");
        cardBox.setId(deckBoxId);
        when(mockBoxAndCardService.getCardBoxInformation(99)).thenReturn(cardBox);
        when(mockBoxAndCardService.checkIfCardIsNull(3, "deck")).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/deleteCard/{type}/{id}","deck","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("boxId", Integer.toString(deckBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "cardBox");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView,"cardBox", cardBox);
    }
    @Test
    public void deleteLoanCard() throws Exception {
        int loanBoxId = 99;
        CardBox cardBox = new CardBox("test box", "hidden", "black");
        cardBox.setId(loanBoxId);
        when(mockBoxAndCardService.getCardBoxInformation(99)).thenReturn(cardBox);
        when(mockBoxAndCardService.checkIfCardIsNull(3, "loan")).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/deleteCard/{type}/{id}","loan","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("boxId", Integer.toString(loanBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "cardBox");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView,"cardBox", cardBox);
    }
    @Test
    public void deleteCollectionCard() throws Exception {
        int collectionBoxId = 99;
        CardBox cardBox = new CardBox("test box", "hidden", "black");
        cardBox.setId(collectionBoxId);
        when(mockBoxAndCardService.getCardBoxInformation(99)).thenReturn(cardBox);
        when(mockBoxAndCardService.checkIfCardIsNull(3, "loan")).thenReturn(false);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/deleteCard/{type}/{id}","collection","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("boxId", Integer.toString(collectionBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "cardBox");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView,"cardBox", cardBox);
    }
}
