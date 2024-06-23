package com.example.aida.Repositories;

import com.example.aida.Entities.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends MongoRepository<CartItem, String> {
    //find by customer id and product id
  //List<CartItem> findByCustomerIdAndProductId(String customerId, String productId);
    // find by product id
    List<CartItem> findByProductId(String productId);
}
