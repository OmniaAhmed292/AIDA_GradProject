package com.example.aida.service.OrderService;

import com.example.aida.Entities.Customer;
import com.example.aida.Entities.Order;
import com.example.aida.Entities.OrderItem;
import com.example.aida.Entities.Product;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.OrderRepository;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.service.ProductService.ProductService;
import com.example.aida.service.UsersService.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public Order getOrderById(String id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.isEmpty() ? null : orders;
    }

    public List<Order> getOrdersByUser() {
        String username = getAuthenticatedUsername();
        Customer customer = customerRepository.findByEmail(username);
        List<Order> orders = orderRepository.findByCustomer(customer.getId());
        return orders.isEmpty() ? null : orders;
    }


    @Transactional
    public Order createOrder(Order order) {
        //Update products because of orderitems
        order.setCreatedAt(LocalDateTime.now());
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProduct().get_id()).get();
            //order.setCustomer(customerRepository.findById(order.getCustomer().get_id()).get());
            orderItem.setProduct(product);
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
