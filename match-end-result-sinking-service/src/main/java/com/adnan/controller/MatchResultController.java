package com.adnan.controller;

import com.adnan.dto.MatchResultDTO;
import com.adnan.service.MatchResultService;
import com.adnan.model.MatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class MatchResultController {
    private final MatchResultService matchResultService;

    public MatchResultController(MatchResultService matchResultService) {
        this.matchResultService = matchResultService;
    }
    @GetMapping("/results")
    public ResponseEntity<List<MatchResult>> getAll() {
        return ResponseEntity.ok(matchResultService.getAll());
    }
    @PostMapping("/save-end-result")
    public ResponseEntity<Void> add(@RequestBody MatchResultDTO request) {
        matchResultService.add(request);
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }

    @GetMapping("/best-scoring-home-team")
    public String getBestScoringHomeTeam() {
        return matchResultService.getBestScoringHomeTeam();
    }
}
