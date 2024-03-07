package com.example.aida.Repositories;

import com.example.aida.Entities.Customer;
import jakarta.transaction.Transactional;

@Transactional
public interface CustomerRepository extends UserRepository<Customer> {


}