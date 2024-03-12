package com.maciejfranikowski.magicTheGatheringCardOrganizer.controller;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.BoxAndCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrganizerController {
    private final BoxAndCardService boxAndCardService;

    public OrganizerController(@Autowired BoxAndCardService boxAndCardService) {
        this.boxAndCardService = boxAndCardService;
    }

    @GetMapping("/")
    public String getBoxes(Model m){
        m.addAttribute("boxes",boxAndCardService.getCardBoxes());
        return "index";
    }
}
