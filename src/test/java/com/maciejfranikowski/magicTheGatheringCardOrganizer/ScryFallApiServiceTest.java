package com.maciejfranikowski.magicTheGatheringCardOrganizer;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.api.AutoCompleteCards;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.ScryFallApiService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/application-test.properties")
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
    @Test
    public void testSearchAutoCompleteCardNames_HttpClientError() {
        when(mockRestTemplate.getForObject(anyString(), any())).thenThrow(HttpClientErrorException.class);
        List<String> result = scryFallApiService.searchAutoCompleteCardNames("query");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchAutoCompleteCardNames_HttpServerError() {
        when(mockRestTemplate.getForObject(anyString(), any())).thenThrow(HttpServerErrorException.class);
        List<String> result = scryFallApiService.searchAutoCompleteCardNames("query");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchAutoCompleteCardNames_RestClientException() {
        when(mockRestTemplate.getForObject(anyString(), any())).thenThrow(RestClientException.class);
        List<String> result = scryFallApiService.searchAutoCompleteCardNames("query");
        assertTrue(result.isEmpty());
    }
}
