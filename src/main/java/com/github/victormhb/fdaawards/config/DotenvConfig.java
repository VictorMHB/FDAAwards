package com.github.victormhb.fdaawards.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
@PropertySource("classpath:application.properties")
public class DotenvConfig {

    private static ConfigurableEnvironment environment;

    public DotenvConfig(ConfigurableEnvironment environment) {
        this.environment =  environment;
    }

    @PostConstruct
    public void init(){
        Dotenv dotEnv = Dotenv.configure().ignoreIfMissing().load();
        dotEnv.entries().forEach((entry) ->
            environment.getSystemProperties().putIfAbsent(entry.getKey(), entry.getValue())
        );
    }
}
