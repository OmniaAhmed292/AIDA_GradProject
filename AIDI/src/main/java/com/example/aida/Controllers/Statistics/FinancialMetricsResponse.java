package com.example.aida.Controllers.Statistics;

import com.example.aida.Entities.Product;
import java.util.List;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
public class FinancialMetricsResponse {
    private List<Product> topSellingProducts;
}
