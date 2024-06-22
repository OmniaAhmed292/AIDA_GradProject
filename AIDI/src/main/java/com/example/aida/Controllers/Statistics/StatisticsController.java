package com.example.aida.Controllers.Statistics;

import com.example.aida.Entities.Product;
import com.example.aida.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

        @Autowired
        private StatisticsService statisticsService;

        @GetMapping("/salesTrends")
        public ResponseEntity<SalesTrendsResponse> getSalesTrends(){
           return ResponseEntity.ok(statisticsService.getSalesTrends());
        }

        @GetMapping("/financialMetrics")
        public ResponseEntity<FinancialMetricsResponse> getFinancialMetrics(){
            return ResponseEntity.ok(statisticsService.getFinancialMetrics());
        }

        @GetMapping("/customerInsights")
        public ResponseEntity<CustomerInsightsResponse> getCustomerInsights(){
            return ResponseEntity.ok(statisticsService.getCustomerInsights());
        }

        @GetMapping("/outOfStock/{page}")
        public ResponseEntity<List<Product>> getOutOfStock(@PathVariable(name = "page") int page){
            return ResponseEntity.ok(statisticsService.getOutOfStock(page));
        }


}
