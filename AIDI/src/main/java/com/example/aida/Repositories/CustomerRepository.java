package com.example.aida.Repositories;

import com.example.aida.Entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.mongodb.repository.MongoRepository;

@Transactional
public interface CustomerRepository extends MongoRepository<Customer, String> {

    //public Optional<Customer> findById(@NonNull String id);
    <Optional> Customer findByEmail(String email);

}