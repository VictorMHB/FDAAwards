package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.dto.user.CreateUserDTO;
import com.github.victormhb.fdaawards.dto.user.UserDTO;
import com.github.victormhb.fdaawards.dto.user.UserUpdateDTO;
import com.github.victormhb.fdaawards.exception.BusinessRuleException;
import com.github.victormhb.fdaawards.exception.ResourceNotFoundException;
import com.github.victormhb.fdaawards.repository.UserRepository;
import com.github.victormhb.fdaawards.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getNickname(),
                        user.getEmail(),
                        user.getRole().name()
                ))
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getNickname(),
                        user.getEmail(),
                        user.getRole().name()
                ))
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrada."));
    }

    public UserDTO createUser(CreateUserDTO dto) {
        Optional<User> existingEmail = userRepository.findByEmail(dto.getEmail());
        if (existingEmail.isPresent()) {
            throw new BusinessRuleException("E-mail já cadastrado.");
        }

        User novo = new User(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                User.Role.valueOf(dto.getRole().toUpperCase())
        );
        novo.setNickname(dto.getNickname());
        novo.setVerified(false);
        String verificationToken = UUID.randomUUID().toString();
        novo.setVerificationToken(verificationToken);

        User salvo = userRepository.save(novo);

        emailService.sendVerificationEmail(salvo, verificationToken);

        return new UserDTO(
                salvo.getId(),
                salvo.getNickname(),
                salvo.getEmail(),
                salvo.getRole().name()
        );
    }

    public UserDTO updateUser(Long id, UserUpdateDTO dto, Long currentUserId) {
        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrada."));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado."));

        boolean isAdmin = currentUser.getRole() == User.Role.ADMIN;

        if (!id.equals(currentUser.getId()) && !isAdmin) {
            throw new AccessDeniedException("Você não tem permissão para editaar este usuário");
        }

        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            Optional<User> byEmail = userRepository.findByEmail(dto.getEmail());
            if (byEmail.isPresent() && !byEmail.get().getId().equals(id)) {
                throw new BusinessRuleException("Email já em uso por outro usuário.");
            }

            targetUser.setEmail(dto.getEmail());
        }

        if (dto.getNickname() != null && !dto.getNickname().isEmpty()) {
            Optional<User> byNickname = userRepository.findByNickname(dto.getNickname());
            if (byNickname.isPresent() && !byNickname.get().getId().equals(id)) {
                throw new BusinessRuleException("Usuário já em uso por outro usuário.");
            }

            targetUser.setNickname(dto.getNickname());
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            targetUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRole() != null && !dto.getRole().isEmpty()) {
            if (!isAdmin) {
                throw new BusinessRuleException("Somente administradores podem alterar a 'role' de um usuário.");
            }

            try {
                User.Role newRole =  User.Role.valueOf(dto.getRole().toUpperCase());
                targetUser.setRole(newRole);
            } catch (IllegalArgumentException e) {
                throw new BusinessRuleException("Role inválida. Use ADMIN ou VOTER.");
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
            throw new ResourceNotFoundException("Enquete com ID " + id + " não encontrada.");
        }

        userRepository.deleteById(id);
    }

    public void verifyUser(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token de verificaação inválido."));

        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }


}
