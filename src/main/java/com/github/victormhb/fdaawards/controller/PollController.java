package com.github.victormhb.fdaawards.controller;

import com.github.victormhb.fdaawards.dto.poll.PollCreateRequest;
import com.github.victormhb.fdaawards.dto.poll.PollResultDTO;
import com.github.victormhb.fdaawards.repository.entity.Poll;
import com.github.victormhb.fdaawards.service.PollService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls")
public class PollController {

    private PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@Valid @RequestBody PollCreateRequest request) {
        return ResponseEntity.ok(pollService.createPoll(request)); //Retorna 200
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getAllPolls() {
        return ResponseEntity.ok(pollService.findAll()); //Retorna 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPollById(@PathVariable Long id) {
        return ResponseEntity.ok(pollService.findById(id)); //Retorna 200
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<PollResultDTO> getPollResult(@PathVariable Long id) {
        return ResponseEntity.ok(pollService.getResults(id));
    }

}
