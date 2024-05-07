package com.example.aida.Repositories;

import com.example.aida.Entities.Product;
import com.example.aida.Enums.SortFeild;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    public Optional<Product> findById(String id);

    public Optional<List<Product>> findByCategoryNameAndIsShown(String categoryName, Boolean isShown, Pageable pageable);

    public Optional<List<Product>> findByVendorIdAndIsShownOrderByUpdatedAt(String vendorId, Boolean isShown);

    List<Product> productSearch(String search, Double minRating, Double minPrice,
                                Double maxPrice, Boolean available, Boolean is_used, SortFeild sortFeild, Boolean discount, Pageable pageable);


    //search for similar products
    public List<Product> findByTagsTagNameInAndIsShownOrderByPriceAsc(List<String> tagNames, Boolean isShown, Pageable pageable);

    //get products in event banner
    public List<Product> findByIsInEventAndIsShownOrderByUpdatedAtDesc(Boolean isInEvent, Boolean isShown ,Pageable pageable);

    //get on sale products
    public List<Product> findByDiscountPercentageGreaterThanAndIsShownOrderByPriceAsc(Double percentage, Boolean isShown, Pageable pageable);

    //get latest created products
    public List<Product> findByIsShownOrderByCreatedAtDesc(Boolean isShown, Pageable pageable);

    //get products for prices under a certain price
    public List<Product> findByPriceLessThanAndIsShownOrderByPriceAsc(BigDecimal price, Boolean isShown, Pageable pageable);

    //TODO: get recommended products

}

