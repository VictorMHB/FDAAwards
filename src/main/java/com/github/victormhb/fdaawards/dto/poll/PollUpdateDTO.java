package com.github.victormhb.fdaawards.dto.poll;

import com.github.victormhb.fdaawards.model.Poll;

import java.time.LocalDateTime;

public class PollUpdateDTO {
    private LocalDateTime openingDate;
    private LocalDateTime closingDate;
    private Poll.Status status;

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public Poll.Status getStatus() {
        return status;
    }

    public void setStatus(Poll.Status status) {
        this.status = status;
    }
}
