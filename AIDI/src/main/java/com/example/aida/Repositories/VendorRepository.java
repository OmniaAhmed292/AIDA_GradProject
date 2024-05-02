package com.example.aida.Repositories;

import com.example.aida.Entities.Vendor;
import jakarta.transaction.Transactional;
import org.springframework.data.mongodb.repository.MongoRepository;

@Transactional
public interface VendorRepository extends MongoRepository<Vendor, String> {
    // Additional methods specific to Vendor (if needed)
    <Optional> Vendor findById(String id);
}