package com.github.victormhb.fdaawards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.github.victormhb.fdaawards.model.User;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
//    @Autowired
//    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user, String verificationToken) {
        String verificationLink = "http://localhost:8080/auth/verify?token=" +  verificationToken;

        System.out.println("===========================");
        System.out.println("Enviando Email de crionte para " + user.getUsername());
        System.out.println("Link para verificação: " +  verificationLink);
        System.out.println("===========================");

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(user.getEmail());
//        message.setSubject("Confirmação de E-mail - FDA Awards");
//        message.setText("Email enviado para " + user.getUsername() +
//                "\n\nPor favor, confirme seu e-mail clicando no link abaixo:\n" +
//                verificationLink +
//                "\n\nObrigado!");
//        mailSender.send(message);
    }
}
