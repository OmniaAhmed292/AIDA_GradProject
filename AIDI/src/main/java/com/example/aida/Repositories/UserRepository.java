package com.example.aida.Repositories;

import com.example.aida.Entities.ConfirmationToken;
import com.example.aida.Entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;


public interface UserRepository  extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);

//todo: update by email

}
