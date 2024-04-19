package com.example.aida.Repositories;

import com.example.aida.Entities.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
}
