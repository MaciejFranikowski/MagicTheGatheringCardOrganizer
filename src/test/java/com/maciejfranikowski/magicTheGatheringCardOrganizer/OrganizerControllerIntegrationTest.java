package com.maciejfranikowski.magicTheGatheringCardOrganizer;


import com.maciejfranikowski.magicTheGatheringCardOrganizer.repository.CardBoxDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
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
    @Value("${sql.scripts.create.box}")
    private String createBoxSql;
    @Autowired
    private CardBoxDao cardBoxDao;
    @BeforeEach
    public void setUpDatabase(){
        jdbcTemplate.execute(createBoxSql);
    }
    @AfterEach
    public void cleanUpDatabase(){
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

}
