package com.github.victormhb.fdaawards.dto;

import jakarta.validation.constraints.NotNull;

public class VoteRequest {

    @NotNull(message = "O ID da enquete é obrigatório.")
    private Long pollId;

    @NotNull(message = "O ID da opção é obrigatório.")
    private Long optionId;

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }
}
