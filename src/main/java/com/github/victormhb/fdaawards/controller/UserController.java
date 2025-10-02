package com.github.victormhb.fdaawards.controller;

import com.github.victormhb.fdaawards.dto.user.CreateUserDTO;
import com.github.victormhb.fdaawards.dto.user.UserDTO;
import com.github.victormhb.fdaawards.dto.user.UserUpdateDTO;
import com.github.victormhb.fdaawards.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto)); //Retorna 200
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        Long currentUserId = extractCurrentUserId();
        UserDTO updated = userService.updateUser(id,  dto, currentUserId);
        return ResponseEntity.ok(updated); //Retorna 200
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUserMe(@Valid @RequestBody UserUpdateDTO dto) {
        Long currentUserId = extractCurrentUserId();
        UserDTO updated = userService.updateUser(currentUserId,  dto, currentUserId);
        return ResponseEntity.ok(updated); //Retorna 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); //Retorna 204
    }

    private Long extractCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof com.github.victormhb.fdaawards.model.User user) {
            return user.getId();
        }

        throw new RuntimeException("Usuário não autenticado ou principal inválido");
    }
}
