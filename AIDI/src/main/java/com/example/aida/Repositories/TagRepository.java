package com.example.aida.Repositories;

import com.example.aida.Entities.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, String> {
    Optional<Tag> findByTagName(String tagName);
    Optional<Tag> findByTagId(String tagId);
    List<Tag> findAll();
}
