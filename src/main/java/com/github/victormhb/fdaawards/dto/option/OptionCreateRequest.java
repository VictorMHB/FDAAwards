package com.github.victormhb.fdaawards.dto.option;

import jakarta.validation.constraints.NotBlank;

public class OptionCreateRequest {

    @NotBlank(message = "O título da opção não pode ser vazio.")
    private String title;

    @NotBlank(message = "A descrição da opção não pode ser vazia.")
    private String description;

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
}
