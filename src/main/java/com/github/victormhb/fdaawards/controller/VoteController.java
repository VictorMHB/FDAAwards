package com.github.victormhb.fdaawards.controller;

import com.github.victormhb.fdaawards.dto.VoteRequest;
import com.github.victormhb.fdaawards.model.Vote;
import com.github.victormhb.fdaawards.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Long> addVote(@Valid @RequestBody VoteRequest request) {
        Vote vote = voteService.registerVote(request);

        return ResponseEntity.ok(vote.getId()); //Retorna 200
    }
}
