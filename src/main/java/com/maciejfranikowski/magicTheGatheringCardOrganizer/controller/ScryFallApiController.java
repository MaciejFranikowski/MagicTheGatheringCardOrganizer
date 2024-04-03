package com.maciejfranikowski.magicTheGatheringCardOrganizer.controller;

import com.maciejfranikowski.magicTheGatheringCardOrganizer.service.ScryFallApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScryFallApiController {
    private final ScryFallApiService scryFallApiService;

    public ScryFallApiController(@Autowired ScryFallApiService scryFallApiService){
        this.scryFallApiService = scryFallApiService;
    }
    @GetMapping("/api/cards/autocomplete/{name}")
    public ResponseEntity<List<String>> searchAutoCompleteCardNames(@PathVariable String name){
        return ResponseEntity.ok(scryFallApiService.searchAutoCompleteCardNames(name));
    }
}
