package com.github.victormhb.fdaawards.dto.user;

public class UserDTO {
    private Long id;
    private String nickname;
    private String email;
    private String role;

    public UserDTO(Long id, String nickname, String email, String role) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
