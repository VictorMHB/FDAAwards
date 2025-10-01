package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.config.JwtAuthFilter;
import com.github.victormhb.fdaawards.model.Poll;
import com.github.victormhb.fdaawards.repository.PollRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PollSchedulingService {
    private final PollRepository pollRepository;
    private final PollService pollService;

    public PollSchedulingService(PollRepository pollRepository, PollService pollService) {
        this.pollRepository = pollRepository;
        this.pollService = pollService;
    }

    @Scheduled(fixedRate = 60000) //60 segundos
    public void checkPollStatus() {
        List<Poll> toOpen = pollService.findPendingToOpen();
        for (Poll poll : toOpen) {
            pollService.updatePollStatus(poll.getId(), Poll.Status.OPEN);
        }

        List<Poll> toClose = pollService.findOpenToClose();
        for (Poll poll : toClose) {
            pollService.updatePollStatus(poll.getId(), Poll.Status.CLOSED);
        }
    }


}
