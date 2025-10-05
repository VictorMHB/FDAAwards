package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.dto.poll.PollCreateRequest;
import com.github.victormhb.fdaawards.dto.poll.PollDTO;
import com.github.victormhb.fdaawards.dto.poll.PollResultDTO;
import com.github.victormhb.fdaawards.dto.poll.PollUpdateDTO;
import com.github.victormhb.fdaawards.exception.BusinessRuleException;
import com.github.victormhb.fdaawards.exception.ResourceNotFoundException;
import com.github.victormhb.fdaawards.repository.PollRepository;
import com.github.victormhb.fdaawards.repository.VoteRepository;
import com.github.victormhb.fdaawards.model.Option;
import com.github.victormhb.fdaawards.model.Poll;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        poll.setOpeningDate(request.getOpeningDate());
        poll.setClosingDate(request.getClosingDate());

        if (poll.getOpeningDate() != null && poll.getClosingDate() != null) {
            if (poll.getOpeningDate().isAfter(poll.getClosingDate())) {
                throw new BusinessRuleException("A data de abertura não pode ser posterior à data de fechamento.");
            }
        }

        poll.setStatus(Poll.Status.PENDING);

        poll.setOptions(request.getOptions().stream().map(optionDto -> {
            Option option = new Option();
            option.setTitle(optionDto.getTitle());
            option.setDescription(optionDto.getDescription());
            option.setPoll(poll);
            return option;
        }).toList());

        return pollRepository.save(poll);
    }

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public Poll findById(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enquete com ID " + id + " não encontrada."));
    }

    @Transactional
    public Poll updatePoll(Long id, PollUpdateDTO dto) {
        Poll poll = findById(id);

        if (poll.getStatus().equals(Poll.Status.CLOSED)) {
            throw new BusinessRuleException("Não é possível editar enquetes que já foram fechadas.");
        }

        if (dto.getTitle() != null) {
            poll.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            poll.setDescription(dto.getDescription());
        }

        if (dto.getOpeningDate() != null) {
            poll.setOpeningDate(dto.getOpeningDate());
        }

        if (dto.getClosingDate() != null) {
            poll.setClosingDate(dto.getClosingDate());
        }

        if (poll.getOpeningDate() != null && poll.getClosingDate() != null) {
            if (poll.getOpeningDate().isAfter(poll.getClosingDate())) {
                throw new BusinessRuleException("A data de abertura não pode ser posterior a data de fechamento");
            }
        }

        if (dto.getStatus() != null) {
            if (dto.getStatus().equals(Poll.Status.CLOSED)) {
                poll.setStatus(Poll.Status.CLOSED);
                poll.setClosingDate(LocalDateTime.now());
            } else if (dto.getStatus().equals(Poll.Status.OPEN)) {
                poll.setStatus(Poll.Status.OPEN);
                poll.setClosingDate(LocalDateTime.now());
            } else {
                poll.setStatus(dto.getStatus());
            }
        }

        return pollRepository.save(poll);
    }

    @Transactional
    public void deletePoll(Long id) {
        Poll poll = findById(id); // já lança exceção se não existir
        pollRepository.delete(poll);
    }

    @Transactional
    public Poll updatePollStatus(Long id, Poll.Status status) {
        Poll poll = findById(id);
        poll.setStatus(status);
        return poll;
    }

    public List<Poll> findPendingToOpen() {
        LocalDateTime now = LocalDateTime.now();
        return pollRepository.findByStatusAndOpeningDateBefore(Poll.Status.PENDING, now);
    }

    public List<Poll> findOpenToClose() {
        LocalDateTime now = LocalDateTime.now();
        return pollRepository.findByStatusAndClosingDateIsNotNullAndClosingDateBefore(Poll.Status.OPEN, now);
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

        return new PollResultDTO(
                poll.getTitle(),
                poll.getStatus(),
                poll.getOpeningDate(),
                poll.getClosingDate(),
                results
        );
    }

    public PollDTO toDTO(Poll poll) {
        return new PollDTO(
                poll.getId(),
                poll.getTitle(),
                poll.getDescription(),
                poll.getStatus(),
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
