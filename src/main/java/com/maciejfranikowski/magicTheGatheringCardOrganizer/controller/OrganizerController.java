package com.maciejfranikowski.magicTheGatheringCardOrganizer.controller;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.models.CardBox;
import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.BoxAndCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/")
    public String createBox(Model m, @ModelAttribute("box") CardBox box){
        boxAndCardService.createCardBox(box.getName(), box.getLocation(), box.getColor());
        m.addAttribute("boxes", boxAndCardService.getCardBoxes());
        return "index";
    }
    @GetMapping("/cardBox/{id}")
    public String getCardBox(@PathVariable int id, Model m){
        if(boxAndCardService.checkIfCardBoxIsNull(id)){
            return "error";
        }
        CardBox cardBox = boxAndCardService.getCardBoxInformation(id);
        m.addAttribute("cardBox", cardBox);
        // TODO: add statistics: number of cards in each column
        return "cardBox";
    }
    @GetMapping("/delete/cardBox/{id}")
    public String deleteCardBox(@PathVariable int id, Model m){
        if(boxAndCardService.checkIfCardBoxIsNull(id))
            return "error";
        boxAndCardService.deleteCardBox(id);
        m.addAttribute("boxes", boxAndCardService.getCardBoxes());
        return "index";
    }
}
