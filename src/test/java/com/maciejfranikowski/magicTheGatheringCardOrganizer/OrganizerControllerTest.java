package com.maciejfranikowski.magicTheGatheringCardOrganizer;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.BoxAndCardService;
import org.junit.jupiter.api.AfterEach;
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
}
