package com.github.victormhb.fdaawards.dto.poll;

import com.github.victormhb.fdaawards.model.Poll;

import java.time.LocalDateTime;
import java.util.List;

public class PollResultDTO {
    private String title;
    private Poll.Status status;
    private LocalDateTime openingDate;
    private LocalDateTime closingDate;
    private List<OptionResultDTO> results;

    public PollResultDTO(String title, Poll.Status status, LocalDateTime openingDate, LocalDateTime closingDate, List<OptionResultDTO> results) {
        this.title = title;
        this.status = status;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.results = results;
    }

    public String getTitle() {
        return title;
    }

    public Poll.Status getStatus() {
        return status;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public List<OptionResultDTO> getResults() {
        return results;
    }

    public static class OptionResultDTO {
        private String title;
        private Long voteCount;

        public OptionResultDTO(String title, Long voteCount) {
            this.title = title;
            this.voteCount = voteCount;
        }

        public String getTitle() {
            return title;
        }

        public Long getVoteCount() {
            return voteCount;
        }
    }
}
