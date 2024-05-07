package com.example.aida.service.OrderService;

import com.example.aida.Entities.Order;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.OrderRepository;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.service.ProductService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;



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


    public Order createOrder(Order order) {
        order.setCreatedAt(java.time.LocalDateTime.now());
        order.setStatus("pending");
        System.out.println(order);
        Double totalPrice = 0.0;
        orderRepository.save(order);
        return order;
//        for (OrderItem orderItem : order.getOrderItems()) {
//            String product_id = orderItem.getProduct();
//            //Product product =productRepository.findById(product_id).get();
//            int orderedQuantity = orderItem.getQuantity();
////            int currentQuantity = product.getQuantity();
////            totalPrice+= product.getPrice() * orderedQuantity;
//            // Ensure there are enough products available
////            if (currentQuantity < orderedQuantity) {
////                throw new RuntimeException("Not enough products available");
////            }
////
////            // Update product quantity
////            product.setQuantity(currentQuantity - orderedQuantity);
//            // Update other product properties if needed
//            // Save or update product
//           // productRepository.save(product);
//        }
//        var new_order= Order.builder()
//                .customer(order.getCustomer())
//                .orderItems(order.getOrderItems())
//                .shipmentPrice(order.getShipmentPrice())
//                .address(order.getAddress())
//                .createdAt(LocalDateTime.now())
//                .status("Pending")
//                .card(order.getCard())
//                .build();
//
//
//
//        // Return the updated order
//        return new_order;
    }


    public Order updateOrder(String id, Order order) {
        order.set_id(id);
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
