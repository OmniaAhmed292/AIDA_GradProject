package com.example.aida.service.ProductService;

import com.example.aida.Entities.Product;
import com.example.aida.Enums.SortFeild;
import com.example.aida.Repositories.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepositoryImpl productRepository;

    @Value("#{systemProperties['QUERY_LIMIT']}")
    private int QUERY_LIMIT;

    @Value("#{systemProperties['PRICE_BANNER']}")

    private float SYSTEM_PRICE_BANNER;


    public ProductService(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(String id){
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> productSearch(String search, Double minRating, Double minPrice,
                                       Double maxPrice, Boolean available, Boolean is_used, SortFeild sortFeild, Boolean discount, int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return productRepository.productSearch(search, minRating, minPrice, maxPrice, available, is_used, sortFeild, discount, pageable);
    }

    public List<Product> getProductByCategory(String categoryName,int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return productRepository.findByCategoryNameAndIsShown(categoryName, true, pageable).orElse(null);
    }

    public List<Product> getProductsOfVendor(String vendorId){
        return productRepository.findByVendorIdAndIsShownOrderByUpdatedAt(vendorId, true).orElse(null);
    }

    public List<Product> getSimilarProducts(String productId){
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null){
            return new ArrayList<>();
        }
        List<String> tagNames = new ArrayList<>();
        product.getTags().forEach(tag -> tagNames.add(tag.getTagName()));

        Pageable pageable = PageRequest.of(0, QUERY_LIMIT);
        return productRepository.findByTagsTagNameInAndIsShownOrderByPriceAsc(tagNames, true, pageable);
    }

    public List<Product> getProductsInEventBanner(int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return productRepository.findByIsInEventAndIsShownOrderByUpdatedAtDesc(true, true, pageable);
    }

    public List<Product> getOnSaleProducts(int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return productRepository.findByDiscountPercentageGreaterThanAndIsShownOrderByPriceAsc(0.0, true, pageable);
    }

    public List<Product> getLatestProducts(int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return productRepository.findByIsShownOrderByCreatedAtDesc(true, pageable);
    }

    public List<Product> getProductsUnderPrice(int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        BigDecimal price = new BigDecimal(SYSTEM_PRICE_BANNER);
        return productRepository.findByPriceLessThanAndIsShownOrderByPriceAsc(price, true, pageable);
    }

}
