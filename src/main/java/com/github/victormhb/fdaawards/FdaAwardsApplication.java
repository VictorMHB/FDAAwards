package com.github.victormhb.fdaawards;

import io.github.cdimascio.dotenv.DotEnvException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FdaAwardsApplication {

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load();

            dotenv.entries().forEach(entry ->
                    System.setProperty(entry.getKey(), entry.getValue())
            );

        } catch (Exception e) {
            System.err.println("Atenção: Arquivo .env não encontrado. Certifique-se de que as variáveis de ambiente estão definidas.");
        }

        SpringApplication.run(FdaAwardsApplication.class, args);
    }

}
