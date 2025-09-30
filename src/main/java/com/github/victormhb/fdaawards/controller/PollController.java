package com.github.victormhb.fdaawards.controller;

import com.github.victormhb.fdaawards.dto.poll.PollCreateRequest;
import com.github.victormhb.fdaawards.dto.poll.PollDTO;
import com.github.victormhb.fdaawards.dto.poll.PollResultDTO;
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
    public ResponseEntity<PollDTO> createPoll(@Valid @RequestBody PollCreateRequest request) {
        return ResponseEntity.ok(pollService.createPollAndReturnDTO(request)); //Retorna 200
    }

    @GetMapping
    public ResponseEntity<List<PollDTO>> getAllPolls() {
        return ResponseEntity.ok(pollService.findAllDTO()); //Retorna 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollDTO> getPollById(@PathVariable Long id) {
        return ResponseEntity.ok(pollService.findByIdDTO(id)); //Retorna 200
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<PollResultDTO> getPollResult(@PathVariable Long id) {
        return ResponseEntity.ok(pollService.getResults(id));
    }

}
