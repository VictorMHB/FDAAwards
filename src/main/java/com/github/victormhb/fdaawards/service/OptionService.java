package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.dto.option.OptionDTO;
import com.github.victormhb.fdaawards.dto.option.OptionUpdateDTO;
import com.github.victormhb.fdaawards.exception.BusinessRuleException;
import com.github.victormhb.fdaawards.exception.ResourceNotFoundException;
import com.github.victormhb.fdaawards.model.Option;
import com.github.victormhb.fdaawards.model.Poll;
import com.github.victormhb.fdaawards.repository.OptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OptionService {
    private OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Transactional
    public OptionDTO updateOption(Long optionId, OptionUpdateDTO dto) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Opção com ID " + optionId + " não encontrada."));

        Poll poll = option.getPoll();
        if (poll.getStatus().equals(Poll.Status.CLOSED)) {
            throw new BusinessRuleException("Não é possível editar opções de uma enquete fechada.");
        }

        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            option.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            option.setDescription(dto.getDescription());
        }

        optionRepository.save(option);
        return toDTO(option);
    }

    public OptionDTO toDTO(Option option) {
        return new OptionDTO(
                option.getId(),
                option.getTitle(),
                option.getDescription()
        );
    }
}
