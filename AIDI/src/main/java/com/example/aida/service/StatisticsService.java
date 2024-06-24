package com.example.aida.service;

import com.example.aida.Controllers.Statistics.CustomerInsightsResponse;
import com.example.aida.Controllers.Statistics.FinancialMetricsResponse;
import com.example.aida.Controllers.Statistics.SalesTrendsResponse;
import com.example.aida.Entities.Product;
import com.example.aida.Entities.Vendor;
import com.example.aida.Repositories.OrderRepository;
import com.example.aida.Repositories.OrderRepositoryImpl;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.Repositories.ProductRepositoryImpl;
import com.example.aida.auth.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepositoryImpl orderRepositoryImpl;

    @Autowired
    private Authorization authorization;
    @Autowired
    private ProductRepositoryImpl productRepositoryImpl;

    public SalesTrendsResponse getSalesTrends() {
        Vendor vendor = authorization.getVendorInfo();
        SalesTrendsResponse salesTrendsResponse = new SalesTrendsResponse();
        salesTrendsResponse.setThisYear(orderRepositoryImpl.getMonthlyRevenues(2024, vendor.getId()));
        salesTrendsResponse.setLastYear(orderRepositoryImpl.getMonthlyRevenues(2023, vendor.getId()));

        return salesTrendsResponse;

    }
    public FinancialMetricsResponse getFinancialMetrics() {
        FinancialMetricsResponse financialMetricsResponse = new FinancialMetricsResponse();
        financialMetricsResponse.setTopSellingProducts(productRepository.findTop3ByIsShownOrderBySalesDesc(true));

        return financialMetricsResponse;
    }
    public CustomerInsightsResponse getCustomerInsights() {
        Vendor vendor = authorization.getVendorInfo();
        CustomerInsightsResponse result = orderRepositoryImpl.getCustomerInsights(vendor.getId());
        result.setPageViews(productRepositoryImpl.sumViewsByVendorId(vendor.getId()));
        result.setConversionRate((result.getFemaleCustomersCount()+0.0+result.getMaleCustomersCount())/result.getPageViews());
        result.setRepeatCustomerPercentage(orderRepositoryImpl.getRepeatCustomerRate(vendor.getId()));
        return result;
    }


    public List<Product> getOutOfStock(int page) {
        Vendor vendor = authorization.getVendorInfo();
        Pageable pageable = PageRequest.of(page-1, 60);
        return productRepository.findByQuantityLessThanAndIsShownAndVendorIdOrderBySubscribersDesc(1 ,true, vendor.getId() ,pageable);
    }

}
