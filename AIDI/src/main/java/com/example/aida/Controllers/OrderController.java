package com.example.aida.Controllers;

import com.example.aida.Entities.Customer;
import com.example.aida.Entities.Order;
import com.example.aida.service.OrderService.OrderService;
import com.example.aida.service.UsersService.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(name = "id") String id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getOrdersByUserAuth(){
        List<Order> orders = orderService.getOrdersByUser();
        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}/{page}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable(name = "userId") String userId, @PathVariable(name = "page") int page){
        List<Order> orders = orderService.getOrdersByUserP(userId, page);
        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/vendor/{page}")
    public ResponseEntity<List<Order>> getOrdersByVendor(@PathVariable(name = "page") int page){
        List<Order> orders = orderService.getOrdersByVendor(page);
        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(name = "id") String id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }


    //Update order status (single item)
    @PutMapping("/updateItemStatus/{id}")
    public ResponseEntity<Order> updateItemStatus(@PathVariable(name = "id") String id, @RequestParam(name = "itemId") String itemId, @RequestParam(name = "status") String status){
        Order updatedOrder = orderService.updateItemStatus(id, itemId, status);
        return ResponseEntity.ok(updatedOrder);
    }


}
