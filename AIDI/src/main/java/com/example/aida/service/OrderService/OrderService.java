package com.example.aida.service.OrderService;

import com.example.aida.Entities.*;
import com.example.aida.Enums.OrderStatus;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.OrderRepository;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.auth.Authorization;
import com.example.aida.service.ProductService.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    @Autowired
    private Authorization authorization;


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


    public List<Order> getOrdersByUserP(String userId, int page) {
        Pageable pageable = PageRequest.of(page-1, 10);
        List<Order> orders = orderRepository.findByCustomer(userId, pageable);
        return orders.isEmpty() ? null : orders;
    }

    public List<Order> getOrdersByVendor(int page) {
        Vendor vendor = authorization.getVendorInfo();
        Pageable pageable = PageRequest.of(page-1, 10);
        List<Order> orders = orderRepository.findByOrderItemsVendorId(vendor.getId(), pageable);

        // Filter the orderItems in each Order
        for (Order order : orders) {
            List<OrderItem> orderItems = order.getOrderItems();
            List<OrderItem> filteredOrderItems = orderItems.stream()
                    .filter(item -> item.getVendorId().equals(vendor.getId()))
                    .collect(Collectors.toList());
            order.setOrderItems(filteredOrderItems);
        }
        return orders.isEmpty() ? null : orders;
    }


    @Transactional
    public Order createOrder(Order order) {
        //Update products because of orderitems
        order.setCreatedAt(LocalDateTime.now());
        for (OrderItem orderItem : order.getOrderItems()) {
            Optional<Product> opProduct = productRepository.findById(orderItem.getProductId());
            Product product;
            product = opProduct.orElseThrow(() -> new RuntimeException("Product not found"));
            //order.setCustomer(customerRepository.findById(order.getCustomer().get_id()).get());
            orderItem.setProductId(product.get_id());
            orderItem.setVendorId(product.getVendorId());
            orderItem.setTaxes(product.getTaxes());

            if(product.getDiscount() != null) {
                orderItem.setDiscountPrice(product.getDiscount().getPercentage()*product.getPrice()/100);
            }
            else {
                orderItem.setDiscountPrice(0.0);
            }

            // create mongodb id
            ObjectIdGenerator objectIdGenerator = new ObjectIdGenerator();
            String id = objectIdGenerator.generate().toString();
            orderItem.setId(id);

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
            try {
                productService.save(product, false);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update product");
            }

        }
        // Save the order

        orderRepository.save(order);

        // Return the updated order
        return order;
    }


    public Order updateOrder(String id, Order order) {
        order.setId(id);
        return orderRepository.save(order);
    }


    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public Order updateItemStatus(String id, String itemId, String status) {
        OrderStatus enumStatus = OrderStatus.valueOf(status);
        Vendor vendor = authorization.getVendorInfo();
        Order order = orderRepository.findById(id).get();
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem updatedOrderItem = null;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getId().equals(itemId) && orderItem.getVendorId().equals(vendor.getId())) {
                orderItem.setStatus(enumStatus);
                updatedOrderItem = orderItem;
                break;
            }
        }

        if(updatedOrderItem == null) {
            throw new RuntimeException("Order item not found");
        }
        //update product sales and revenues if status is delivered
        if (enumStatus == OrderStatus.delivered) {
            Optional<Product> product = productRepository.findById(updatedOrderItem.getProductId());
            if (product.isEmpty()) {
                throw new RuntimeException("Product not found");
            }
            else {
                Product actualProduct = product.get();
                Double discount = updatedOrderItem.getDiscountPrice();
                if (discount != null) {
                    Double profit = updatedOrderItem.getProductPrice() - discount;
                    actualProduct.setRevenue(actualProduct.getRevenue() + profit);
                }
                else
                    actualProduct.setRevenue(actualProduct.getRevenue());
                actualProduct.setSales(actualProduct.getSales() + updatedOrderItem.getQuantity());
                productService.saveProductAsync(actualProduct);
            }
        }

        return orderRepository.save(order);
    }

    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
