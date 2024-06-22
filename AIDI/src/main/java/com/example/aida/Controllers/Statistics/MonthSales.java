package com.example.aida.Controllers.Statistics;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MonthSales {
    private int month;

    private double revenue;

    private int ordersCount;
}
