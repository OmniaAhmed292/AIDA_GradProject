package com.example.aida.service.OrderService;

import com.example.aida.Entities.Order;
import com.example.aida.Entities.OrderItem;
import com.example.aida.Entities.Product;
import com.example.aida.Repositories.OrderRepository;
import com.example.aida.service.ProductService.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;



    public Order getOrderById(String id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.isEmpty() ? null : orders;
    }

    public List<Order> getOrdersByUser(String userId) {
        List<Order> orders = orderRepository.findByCustomer(userId);
        return orders.isEmpty() ? null : orders;
    }


    @Transactional
    public Order createOrder(Order order) {
        //Update products because of orderitems
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            int orderedQuantity = orderItem.getQuantity();
            int currentQuantity = product.getQuantity();

            // Ensure there are enough products available
            if (currentQuantity < orderedQuantity) {
                throw new RuntimeException("Not enough products available");
            }

            // Update product quantity
            product.setQuantity(currentQuantity - orderedQuantity);
            // Update other product properties if needed

            // Save or update product
            productService.save(product);
        }
        // Save the order
         orderRepository.save(order);

        // Return the updated order
        return order;
    }


    public Order updateOrder(String id, Order order) {
        order.setOrderId(id);
        return orderRepository.save(order);
    }

    public Order updatestatus(String id, String status) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(status);
        return orderRepository.save(order);
    }


    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

}
