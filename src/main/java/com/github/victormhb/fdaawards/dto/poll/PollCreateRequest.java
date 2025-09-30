package com.github.victormhb.fdaawards.dto.poll;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class PollCreateRequest {

    @NotBlank(message = "O título da enquete não pode ser vazio.")
    private String title;

    @NotBlank(message = "A descrição da enquete não pode ser vazio.")
    private String description;

    @NotEmpty(message = "Uma enquete deve ter pelo menos uma opção.")
    @Valid
    private List<OptionCreateRequest> options;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OptionCreateRequest> getOptions() {
        return options;
    }

    public void setOptions(List<OptionCreateRequest> options) {
        this.options = options;
    }
}
