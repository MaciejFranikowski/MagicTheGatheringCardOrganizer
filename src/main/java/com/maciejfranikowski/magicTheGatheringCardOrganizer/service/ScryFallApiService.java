package com.maciejfranikowski.magicTheGatheringCardOrganizer.service;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.api.ApiCard;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.api.AutoCompleteCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ScryFallApiService {
    private final String SCRYFALL_API_AUTOCOMPLETE_URL="https://api.scryfall.com/cards/autocomplete?q=";
    private final String SCRYFALL_API_SEARCH_NAMED_URL="https://api.scryfall.com/cards/named?fuzzy=";
    private final Logger logger;
    private final RestTemplate restTemplate;
    public ScryFallApiService(@Autowired RestTemplate restTemplate){
       this.logger = Logger.getLogger(getClass().getName());
       this.restTemplate = restTemplate;
    }

    public List<String> searchAutoCompleteCardNames(String query) {
        try {
            AutoCompleteCards cards = this.restTemplate.getForObject(
                    SCRYFALL_API_AUTOCOMPLETE_URL + query,
                    AutoCompleteCards.class
            );
            assert cards != null;
            return cards.getData();
        } catch (HttpClientErrorException | HttpServerErrorException httpClientException) {
            logger.warning("Error calling external API: " +
                    httpClientException.getStatusCode() + " " + httpClientException.getStatusText());
            return Collections.emptyList();
        } catch (RestClientException restClientException) {
            logger.severe("Error calling external API: " + restClientException.getMessage());
            return Collections.emptyList();
        }
    }
    public ApiCard searchNamedCard(String query) {
        try {
            return this.restTemplate.getForObject(
                    SCRYFALL_API_SEARCH_NAMED_URL + query,
                    ApiCard.class
            );
        } catch (HttpClientErrorException | HttpServerErrorException httpClientException) {
            logger.warning("Error calling external API: " +
                    httpClientException.getStatusCode() + " " + httpClientException.getStatusText());
//            return Collections.emptyList();
        } catch (RestClientException restClientException) {
            logger.severe("Error calling external API: " + restClientException.getMessage());
//            return Collections.emptyList();
        }
        return null;
    }
}
