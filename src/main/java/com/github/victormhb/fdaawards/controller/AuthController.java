package com.github.victormhb.fdaawards.controller;

import com.github.victormhb.fdaawards.config.JwtUtil;
import com.github.victormhb.fdaawards.dto.AuthRequest;
import com.github.victormhb.fdaawards.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

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

}
