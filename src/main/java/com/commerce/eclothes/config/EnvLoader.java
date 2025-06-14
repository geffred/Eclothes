package com.commerce.eclothes.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class EnvLoader {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
}
