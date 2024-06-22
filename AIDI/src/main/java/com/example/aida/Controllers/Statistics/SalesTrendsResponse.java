package com.example.aida.Controllers.Statistics;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class SalesTrendsResponse {
    private List<MonthSales> thisYear;
    private List<MonthSales> lastYear;

    private Integer ordersCount;
    private Double revenue;

    public void setThisYear(List<MonthSales> thisYear) {
        this.thisYear = thisYear;
        // set revenue and ordersCount
        this.revenue = thisYear.stream().mapToDouble(MonthSales::getRevenue).sum();
        this.ordersCount = thisYear.stream().mapToInt(MonthSales::getOrdersCount).sum();
    }
}
