package com.github.victormhb.fdaawards.dto.poll;

import com.github.victormhb.fdaawards.model.Poll;

import java.util.List;

public class PollDTO {

    private Long id;
    private String title;
    private String description;
    private Poll.Status status;
    private List<OptionDTO> options;

    public PollDTO(Long id, String title, String description, Poll.Status status, List<OptionDTO> options) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Poll.Status getStatus() {
        return status;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    public static class OptionDTO {
        private Long id;
        private String title;
        private String description;

        public OptionDTO(Long id, String title, String description) {
            this.id = id;
            this.title = title;
            this.description = description;
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
