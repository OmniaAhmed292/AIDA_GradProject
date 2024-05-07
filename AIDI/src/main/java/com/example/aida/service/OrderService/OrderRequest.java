package com.example.aida.service.OrderService;

import com.example.aida.Entities.Address;
import com.example.aida.Entities.Card;
import com.example.aida.Entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Double shipmentPrice;
    private Address address;
    private String customer;
    private Double pointDiscountPercentage;
    private List<OrderItem> orderItems;
    private Card card;

}
