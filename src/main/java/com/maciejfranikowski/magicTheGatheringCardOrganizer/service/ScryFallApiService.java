package com.maciejfranikowski.magicTheGatheringCardOrganizer.service;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.api.AutocompleteCards;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ScryFallApiService {
    private final String SCRYFALL_API_AUTOCOMPLETE_URL="https://api.scryfall.com/cards/autocomplete?q=";
    private final Logger logger;
    public ScryFallApiService(){
       this.logger = Logger.getLogger(getClass().getName());
    }

    public List<String> searchAutoCompleteCardNames(String query){
        RestTemplate restTemplate = new RestTemplate();
        AutocompleteCards cards = restTemplate.getForObject(SCRYFALL_API_AUTOCOMPLETE_URL+query, AutocompleteCards.class);
        logger.info(cards.toString());
        return cards.getData();
//        return Arrays.asList(cards);
    }
}
