package com.github.victormhb.fdaawards.dto.poll;

import java.util.List;

public class PollDTO {

    private Long id;
    private String title;
    private String description;
    private List<OptionDTO> options;

    public PollDTO(Long id, String title, String description, List<OptionDTO> options) {
        this.id = id;
        this.title = title;
        this.description = description;
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
