package com.maciejfranikowski.magicTheGatheringCardOrganizer.controller;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.BoxAndCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizerController {
    private BoxAndCardService boxAndCardService;

    public OrganizerController(@Autowired BoxAndCardService boxAndCardService) {
        this.boxAndCardService = boxAndCardService;
    }
}
