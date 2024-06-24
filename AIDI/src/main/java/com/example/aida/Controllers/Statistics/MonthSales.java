package com.example.aida.Controllers.Statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MonthSales {
    @Id
    private int month;

    private double revenue;

    private int ordersCount;
}
