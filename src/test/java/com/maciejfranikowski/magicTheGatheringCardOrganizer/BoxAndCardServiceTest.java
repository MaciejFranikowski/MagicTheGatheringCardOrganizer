package com.maciejfranikowski.magicTheGatheringCardOrganizer;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.BoxAndCardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class BoxAndCardServiceTest {
    @Value("${sql.scripts.create.box}")
    private String createBoxSql;
    @Value("${sql.scripts.delete.box}")
    private String deleteBoxSql;

    @Autowired
    private BoxAndCardService boxAndCardService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEachSql(){
        jdbcTemplate.execute(createBoxSql);
    }
    @AfterEach
    public void cleanUpDatabase(){
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

}
