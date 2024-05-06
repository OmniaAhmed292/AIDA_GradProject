package com.example.aida.Repositories;

import com.example.aida.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {


    List<Order> findByCustomer(String CustomerId);
}
