package com.example.aida.service.OrderService;

import com.example.aida.Entities.Order;
import com.example.aida.Entities.OrderItem;
import com.example.aida.Entities.Product;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.OrderRepository;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.service.ProductService.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<Order> getOrdersByUser(String userId, int page) {
        Pageable pageable = PageRequest.of(page-1, 10);
        List<Order> orders = orderRepository.findByCustomer(userId, pageable);
        return orders.isEmpty() ? null : orders;
    }


    @Transactional
    public Order createOrder(Order order) {
        //Update products because of orderitems
        order.setCreatedAt(LocalDateTime.now());
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProductId()).get();
            //order.setCustomer(customerRepository.findById(order.getCustomer().get_id()).get());
            orderItem.setProductId(product.get_id());
            orderItem.setVendorId(product.getVendorId());
            System.out.println(product);
            //Product product = orderItem.getProduct();
            int orderedQuantity = orderItem.getQuantity();
            int currentQuantity = product.getQuantity();

            // Ensure there are enough products available
            if (currentQuantity < orderedQuantity) {
                throw new RuntimeException("Not enough products available");
            }

            // Update product quantity
            product.setQuantity(currentQuantity - orderedQuantity);
            product.setSales(product.getSales() + orderedQuantity);
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
        order.set_id(id);
        return orderRepository.save(order);
    }


    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public Order updateItemStatus(String id, String itemId, String status) {
        Order order = orderRepository.findById(id).get();
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.get_id().equals(itemId)) {
                orderItem.setStatus(status);
                break;
            }
        }
        return orderRepository.save(order);
    }
}
