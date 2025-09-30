package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.dto.VoteRequest;
import com.github.victormhb.fdaawards.model.Option;
import com.github.victormhb.fdaawards.model.Vote;
import com.github.victormhb.fdaawards.repository.OptionRepository;
import com.github.victormhb.fdaawards.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private VoteRepository voteRepository;
    private OptionRepository optionRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, OptionRepository optionRepository) {
        this.voteRepository = voteRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public Vote registerVote(VoteRequest request) {
        Long userId = getCurrentAuthenticatedUserId();

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
