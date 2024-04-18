package com.example.aida.Repositories;

import com.example.aida.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserRepository  extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);

//todo: update by email



}
