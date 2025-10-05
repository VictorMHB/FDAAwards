package com.github.victormhb.fdaawards.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserDTO {

    @NotBlank(message = "O usuário não pode ser vazio")
    private String nickname;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha não pode ser vazia")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

    private String role;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String username) {
        this.nickname = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
