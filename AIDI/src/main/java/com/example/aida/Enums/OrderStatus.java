package com.example.aida.Enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum OrderStatus {
    pending("pending"),
    shipped("shipped"),
    delivered("delivered");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

}
