package com.example.aida.service.ProductService;


import com.example.aida.Enums.SortFeild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductSearchRequest {

    private String search;
    private Double minRating;
    private Double minPrice;
    private Double maxPrice;
    private Boolean available;
    private Boolean is_used;
    private SortFeild sortFeild;
    private Boolean discount; //is there a discount or not
    private int page;

}
