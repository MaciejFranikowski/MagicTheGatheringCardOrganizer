package com.maciejfranikowski.magicTheGatheringCardOrganizer;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.api.AutoCompleteCards;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.ScryFallApiService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ScryFallApiServiceTest {
    @Mock
    RestTemplate mockRestTemplate;
    @InjectMocks
    private ScryFallApiService scryFallApiService;

    @Test
    public void testSearchAutoCompleteNames_Success(){
        AutoCompleteCards autoCompleteCards = new AutoCompleteCards();
        autoCompleteCards.setData(List.of("card1", "card2"));
        when(mockRestTemplate.getForObject(anyString(), any())).thenReturn(autoCompleteCards);
        List<String> cards = scryFallApiService.searchAutoCompleteCardNames("query");
        assertEquals(autoCompleteCards.getData(), cards);
    }
}
