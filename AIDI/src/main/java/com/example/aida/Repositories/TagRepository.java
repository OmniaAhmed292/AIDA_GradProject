package com.example.aida.Repositories;

import com.example.aida.Entities.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, String> {
    Optional<Tag> findByTagName(String tagName);

    List<Tag> findAll();
}
