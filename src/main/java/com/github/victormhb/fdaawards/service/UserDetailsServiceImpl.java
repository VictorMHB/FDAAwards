package com.github.victormhb.fdaawards.service;

import com.github.victormhb.fdaawards.exception.BusinessRuleException;
import com.github.victormhb.fdaawards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .or(() -> userRepository.findByEmail(email))
                .orElseThrow(( ) -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        if (!user.isVerified()) {
            throw new BusinessRuleException("Conta não verificada. Verifique seu email antes de fazer login.");
        }

        return user;
    }


}
