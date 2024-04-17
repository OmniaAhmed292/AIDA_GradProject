package com.example.aida.Repositories;

import com.example.aida.Entities.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, Long> {
    <optional> ConfirmationToken findByToken(String token);
}
