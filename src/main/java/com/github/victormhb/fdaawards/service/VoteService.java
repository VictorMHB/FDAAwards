package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.dto.VoteRequest;
import com.github.victormhb.fdaawards.model.Option;
import com.github.victormhb.fdaawards.model.Poll;
import com.github.victormhb.fdaawards.model.Vote;
import com.github.victormhb.fdaawards.repository.OptionRepository;
import com.github.victormhb.fdaawards.repository.PollRepository;
import com.github.victormhb.fdaawards.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, OptionRepository optionRepository, PollRepository pollRepository) {
        this.voteRepository = voteRepository;
        this.optionRepository = optionRepository;
        this.pollRepository = pollRepository;
    }

    @Transactional
    public Vote registerVote(VoteRequest request) {
        Long userId = getCurrentAuthenticatedUserId();

        Poll poll = pollRepository.findById(request.getPollId())
                .orElseThrow(() -> new RuntimeException("Enquete não encontrada"));

        if (poll.getStatus() == Poll.Status.CLOSED ||  poll.getStatus() == Poll.Status.PENDING) {
            throw new RuntimeException("Não é possível votar. A enquete está fechada ou não foi aberta");
        }

        Option option = optionRepository.findById(request.getOptionId())
                .orElseThrow(() -> new RuntimeException("Opção de voto não encontrada."));

        if (!option.getPoll().getId().equals(request.getPollId())) {
            throw new RuntimeException("A opção não pertence a enquete fornecida.");
        }

        if (voteRepository.existsByPollIdAndOptionId(request.getPollId(), userId)) {
            throw new RuntimeException("O usuário já votou nesta enquete.");
        }

        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setPoll(option.getPoll());
        vote.setOption(option);

        return voteRepository.save(vote);
    }

    private Long getCurrentAuthenticatedUserId() {
        return ((com.github.victormhb.fdaawards.model.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }


}
