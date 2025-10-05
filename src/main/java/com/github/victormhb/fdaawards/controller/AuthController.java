package com.github.victormhb.fdaawards.controller;

import com.github.victormhb.fdaawards.config.JwtUtil;
import com.github.victormhb.fdaawards.dto.auth.AuthRequest;
import com.github.victormhb.fdaawards.dto.auth.AuthResponse;
import com.github.victormhb.fdaawards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthResponse(token)); //Retorna 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas: " + e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token){
        userService.verifyUser(token);
        return ResponseEntity.ok("Email verificado com sucesso! Agora você pode fazer login."); //Retorna 200
    }

}
