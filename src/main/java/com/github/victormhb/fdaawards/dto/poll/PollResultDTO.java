package com.github.victormhb.fdaawards.dto.poll;

import java.util.List;

public class PollResultDTO {
    private String title;
    private List<OptionResultDTO> results;

    public PollResultDTO(String title, List<OptionResultDTO> results) {
        this.title = title;
        this.results = results;
    }

    public String getTitle() {
        return title;
    }

    public List<OptionResultDTO> getResults() {
        return results;
    }

    public static class OptionResultDTO {
        private String title;
        private long voteCount;

        public OptionResultDTO(String title, long voteCount) {
            this.title = title;
            this.voteCount = voteCount;
        }

        public String getTitle() {
            return title;
        }

        public long getVoteCount() {
            return voteCount;
        }
    }
}
