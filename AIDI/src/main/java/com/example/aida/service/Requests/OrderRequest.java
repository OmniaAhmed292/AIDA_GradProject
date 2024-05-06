package com.example.aida.service.Requests;

import com.example.aida.Entities.Address;
import com.example.aida.Entities.Card;
import com.example.aida.Entities.Customer;
import com.example.aida.Entities.OrderItem;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private BigDecimal shipmentPrice;
    private LocalDateTime createdAt;
    private Address address;
    private List<OrderItem> orderItems;
    private Customer customer;
    private float pointDiscountPercentage;
    private Card card;
}
