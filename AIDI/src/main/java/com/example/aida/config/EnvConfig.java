package com.example.aida.config;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class EnvConfig {

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();
        String tokenLength = dotenv.get("TOKEN_LENGTH");
        if (tokenLength == null) {
            throw new IllegalArgumentException("TOKEN_LENGTH not found in .env file");
        }
        String priceBanner = dotenv.get("PRICE_BANNER");
        if (priceBanner == null) {
            throw new IllegalArgumentException("PRICE_BANNER not found in .env file");
        }
        String queryLimit = dotenv.get("QUERY_LIMIT");
        if (queryLimit == null) {
            throw new IllegalArgumentException("QUERY_LIMIT not found in .env file");
        }
        System.setProperty("TOKEN_LENGTH", tokenLength);
        System.setProperty("PRICE_BANNER", priceBanner);
        System.setProperty("QUERY_LIMIT", queryLimit);

    }
}