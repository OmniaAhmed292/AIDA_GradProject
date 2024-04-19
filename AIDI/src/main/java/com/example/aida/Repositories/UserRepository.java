package com.example.aida.Repositories;

import com.example.aida.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.Optional;


public interface UserRepository  extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(String userId);

    @Query("{ 'confirmationToken.token' : ?0 }")
    Optional<User> findByConfirmationTokenToken(String token);

//todo: update by email

}
