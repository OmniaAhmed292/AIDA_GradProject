package com.example.aida.Repositories;

import com.example.aida.Entities.ProductImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductImageRepository extends MongoRepository<ProductImage, String> {
}
