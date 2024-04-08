package com.maciejfranikowski.magicTheGatheringCardOrganizer;


import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CardBoxDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CollectionCardDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.DeckCardDao;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.LoanCardDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class OrganizerControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${sql.scripts.delete.box}")
    private String deleteBoxSql;
    @Value("${sql.scripts.delete.deck_cards}")
    private String deleteDeckCardsSql;
    @Value("${sql.scripts.delete.collection_cards}")
    private String deleteCollectionCardsSql;
    @Value("${sql.scripts.delete.loan_cards}")
    private String deleteLoanCardsSql;
    @Value("${sql.scripts.create.box}")
    private String createBoxSql;
    @Autowired
    private CardBoxDao cardBoxDao;
    @Autowired
    private DeckCardDao deckCardDao;
    @Autowired
    private LoanCardDao loanCardDao;
    @Autowired
    private CollectionCardDao collectionCardDao;
    @BeforeEach
    public void setUpDatabase(){
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
    public void deleteBoxIntegrationHttpRequest() throws Exception {
        assertTrue(cardBoxDao.findById(99).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete/cardBox/{id}",99)
                ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"index");
        assertFalse(cardBoxDao.findById(99).isPresent());
    }
    @Test
    public void deleteInvalidBoxIntegrationHttpRequest() throws Exception {
        assertFalse(cardBoxDao.findById(2).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete/cardBox/{id}",2)
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"error");
    }
    @Test
    public void createDeckCardHttpRequest() throws Exception {
        String cardName = "Force of Will";
        String deckName = "Grixis Delver";
        int deckBoxId = 99;
        assertTrue(cardBoxDao.findById(deckBoxId).isPresent());
        assertEquals(deckCardDao.findAll().size(), 0);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/create/card/deck")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name",cardName)
                .param("deckName", deckName)
                .param("boxId", Integer.toString(deckBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"cardBox");
        assertEquals(deckCardDao.findAll().size(), 1);
    }
    @Test
    public void createLoanCardHttpRequest() throws Exception {
        String cardName = "Force of Will";
        String ownerFirstName = "John";
        String ownerLastName = "Smith";
        int deckBoxId = 99;
        assertTrue(cardBoxDao.findById(deckBoxId).isPresent());
        assertEquals(loanCardDao.findAll().size(), 0);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/create/card/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name",cardName)
                .param("ownerFirstName", ownerFirstName)
                .param("ownerLastName", ownerLastName)
                .param("boxId", Integer.toString(deckBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"cardBox");
        assertEquals(loanCardDao.findAll().size(), 1);
    }
    @Test
    public void createCollectionCardHttpRequest() throws Exception {
        String cardName = "Force of Will";
        String setName = "Nemesis";
        int deckBoxId = 99;
        assertTrue(cardBoxDao.findById(deckBoxId).isPresent());
        assertEquals(collectionCardDao.findAll().size(), 0);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/create/card/collection")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name",cardName)
                .param("setName", setName)
                .param("boxId", Integer.toString(deckBoxId))
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"cardBox");
        assertEquals(collectionCardDao.findAll().size(), 1);
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteDeckCardHttpRequest() throws Exception {
        assertTrue(cardBoxDao.findById(2).isPresent());
        assertTrue(deckCardDao.findById(66).isPresent());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/deleteCard/{type}/{id}","deck",66)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("boxId","2")
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"cardBox");
        assertTrue(cardBoxDao.findById(2).isPresent());
        assertFalse(deckCardDao.findById(66).isPresent());
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteLoanCardHttpRequest() throws Exception {
        assertTrue(cardBoxDao.findById(2).isPresent());
        assertTrue(loanCardDao.findById(66).isPresent());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/deleteCard/{type}/{id}","loan",66)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("boxId","2")
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"cardBox");
        assertTrue(cardBoxDao.findById(2).isPresent());
        assertFalse(loanCardDao.findById(66).isPresent());
    }
    @Test
    @Sql("/sql/deleteCard.sql")
    public void deleteCollectionCardHttpRequest() throws Exception {
        assertTrue(cardBoxDao.findById(2).isPresent());
        assertTrue(collectionCardDao.findById(66).isPresent());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/deleteCard/{type}/{id}","collection",66)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("boxId","2")
        ).andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView,"cardBox");
        assertTrue(cardBoxDao.findById(2).isPresent());
        assertFalse(collectionCardDao.findById(66).isPresent());
    }
}
