package com.github.victormhb.fdaawards.dto.poll;

import com.github.victormhb.fdaawards.dto.option.OptionCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public class PollCreateRequest {

    @NotBlank(message = "O título da enquete não pode ser vazio.")
    private String title;

    @NotBlank(message = "A descrição da enquete não pode ser vazio.")
    private String description;

    @NotEmpty(message = "Uma enquete deve ter pelo menos uma opção.")
    @Valid
    private List<OptionCreateRequest> options;

    private LocalDateTime openingDate;
    private LocalDateTime closingDate;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<OptionCreateRequest> getOptions() {
        return options;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }
}
