package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.dto.poll.PollCreateRequest;
import com.github.victormhb.fdaawards.dto.poll.PollDTO;
import com.github.victormhb.fdaawards.dto.poll.PollResultDTO;
import com.github.victormhb.fdaawards.repository.PollRepository;
import com.github.victormhb.fdaawards.repository.VoteRepository;
import com.github.victormhb.fdaawards.model.Option;
import com.github.victormhb.fdaawards.model.Poll;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    public PollService(PollRepository pollRepository, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public Poll createPoll(PollCreateRequest request) {
        Poll poll = new Poll();
        poll.setTitle(request.getTitle());
        poll.setDescription(request.getDescription());

        poll.setOptions(request.getOptions().stream().map(optionDto -> {
            Option option = new Option();
            option.setTitle(optionDto.getTitle());
            option.setDescription(optionDto.getDescription());
            option.setPoll(poll);
            return option;
        }).collect(Collectors.toList()));

        return pollRepository.save(poll);
    }

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public Poll findById(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Votação não encontrada"));
    }

    @Transactional
    public PollResultDTO getResults(Long pollId) {
        Poll poll = findById(pollId);

        List<PollResultDTO.OptionResultDTO> results = poll.getOptions().stream()
                .map(option -> {
                    long count =  voteRepository.countByPollIdAndOptionId(pollId, option.getId());
                    return new PollResultDTO.OptionResultDTO(option.getTitle(), count);
                })
                .toList();

        return new PollResultDTO(poll.getTitle(), results);
    }

    public PollDTO toDTO(Poll poll) {
        return new PollDTO(
                poll.getId(),
                poll.getTitle(),
                poll.getDescription(),
                poll.getOptions().stream()
                        .map(option -> new PollDTO.OptionDTO(option.getId(), option.getTitle(), option.getDescription()))
                        .toList()
        );
    }

    @Transactional
    public PollDTO createPollAndReturnDTO(PollCreateRequest request) {
        Poll poll = createPoll(request);
        return toDTO(poll);
    }

    public List<PollDTO> findAllDTO() {
        return findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public PollDTO findByIdDTO(Long id) {
        Poll poll = findById(id);
        return toDTO(poll);
    }


}
