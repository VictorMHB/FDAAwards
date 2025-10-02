package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.dto.user.CreateUserDTO;
import com.github.victormhb.fdaawards.dto.user.UserDTO;
import com.github.victormhb.fdaawards.dto.user.UserUpdateDTO;
import com.github.victormhb.fdaawards.repository.UserRepository;
import com.github.victormhb.fdaawards.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsernameField(),
                        user.getEmail(),
                        user.getRole().name()
                ))
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsernameField(),
                        user.getEmail(),
                        user.getRole().name()
                ))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public UserDTO createUser(CreateUserDTO dto) {
        User novo = new User(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                User.Role.valueOf(dto.getRole().toUpperCase())
        );
        novo.setUsername(dto.getUsername());

        User salvo = userRepository.save(novo);

        return new UserDTO(
                salvo.getId(),
                salvo.getUsernameField(),
                salvo.getEmail(),
                salvo.getRole().name()
        );
    }

    public UserDTO updateUser(Long id, UserUpdateDTO dto, Long currentUserId) {
        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado"));

        boolean isAdmin = currentUser.getRole() == User.Role.ADMIN;

        if (!id.equals(currentUser.getId()) && !isAdmin) {
            throw new AccessDeniedException("Você não tem permissão para editaar este usuário");
        }

        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            Optional<User> byEmail = userRepository.findByEmail(dto.getEmail());
            if (byEmail.isPresent() && !byEmail.get().getId().equals(id)) {
                throw new RuntimeException("Email já em uso por outro usuário.");
            }

            targetUser.setEmail(dto.getEmail());
        }

        if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
            Optional<User> byUsername = userRepository.findByUsername(dto.getUsername());
            if (byUsername.isPresent() && !byUsername.get().getId().equals(id)) {
                throw new RuntimeException("Usuário já em uso por outro usuário.");
            }

            targetUser.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            targetUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRole() != null && !dto.getRole().isEmpty()) {
            if (!isAdmin) {
                throw new RuntimeException("Somente administradores podem alterar a 'role' de um usuário.");
            }

            try {
                User.Role newRole =  User.Role.valueOf(dto.getRole().toUpperCase());
                targetUser.setRole(newRole);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Role inválida. Use ADMIN ou VOTER.");
            }
        }

        User saved = userRepository.save(targetUser);

        return new UserDTO(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRole().name()
        );
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        userRepository.deleteById(id);
    }


}
