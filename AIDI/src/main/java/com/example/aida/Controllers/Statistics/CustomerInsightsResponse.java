package com.example.aida.Controllers.Statistics;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomerInsightsResponse {
    private int minAgeGroup;
    private int maxAgeGroup;
    private Double RepeatCustomerPercentage;
    private Double churnRate;
    private int pageViews;
    private Double conversionRate;
    private int femaleCustomersCount;
    private int maleCustomersCount;
}
