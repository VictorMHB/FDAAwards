package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.dto.CreateUserDTO;
import com.github.victormhb.fdaawards.dto.UserDTO;
import com.github.victormhb.fdaawards.repository.UserRepository;
import com.github.victormhb.fdaawards.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
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
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getUsername(),  user.getRole().name()))
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getRole().name()))
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

        return new UserDTO(salvo.getId(),  salvo.getEmail(), salvo.getUsername(), salvo.getRole().name());
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        userRepository.deleteById(id);
    }


}
