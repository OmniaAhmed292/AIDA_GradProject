package com.example.aida.config;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class EnvConfig {

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("TOKEN_LENGTH", dotenv.get("TOKEN_LENGTH"));
        System.setProperty("PRICE_BANNER", dotenv.get("PRICE_BANNER"));
        System.setProperty("QUERY_LIMIT", dotenv.get("QUERY_LIMIT"));

    }
}