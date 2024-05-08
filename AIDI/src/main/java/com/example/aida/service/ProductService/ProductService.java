package com.example.aida.service.ProductService;

import com.example.aida.Entities.Product;
import com.example.aida.Entities.Tag;
import com.example.aida.Enums.SortFeild;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.Repositories.ProductRepositoryImpl;
import com.example.aida.Repositories.TagRepository;
import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepositoryImpl searchRep;
    private final ProductRepository repository;
    private final TagRepository tagRepository;

    @Value("#{systemProperties['QUERY_LIMIT']}")
    private int QUERY_LIMIT;

    @Value("#{systemProperties['PRICE_BANNER']}")

    private float SYSTEM_PRICE_BANNER;

    //---------------------------------Tags crud---------------------------------
    public List<Tag> getAllTags(){
        List<Tag> tags = new ArrayList<>();
        tags = tagRepository.findAll();
        return tags;
    }

    public Tag getTagById(String id){
        return tagRepository.findById(id).orElse(null);
    }

    public Tag getTagByName(String name){
        return tagRepository.findByTagName(name).orElse(null);
    }

    public Tag saveTag(Tag tag){
        return tagRepository.save(tag);
    }
    //---------------------------------Product Service---------------------------------

    @Nullable
    public Product getProductById(@NonNull String id){
        return repository.findById(id).orElse(null);
    }


    public Product save(Product product) {
        return repository.save(product);
    }

    public Product findById(String id) {
           return repository.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public List<Product> productSearch(String search, Double minRating, Double minPrice,
                                       Double maxPrice, Boolean available, Boolean is_used, SortFeild sortFeild, Boolean discount, int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return searchRep.productSearch(search, minRating, minPrice, maxPrice, available, is_used, sortFeild, discount, pageable);
    }

    public List<Product> getProductByCategory(String categoryName,int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return repository.findByCategoryNameAndIsShown(categoryName, true, pageable).orElse(null);
    }

    public List<Product> getProductsOfVendor(String vendorId){
        return repository.findByVendorIdAndIsShownOrderByUpdatedAt(vendorId, true).orElse(null);
    }

    public List<Product> getSimilarProducts(String productId){
        Product product = repository.findById(productId).orElse(null);
        if(product == null){
            return new ArrayList<>();
        }
        List<String> tagNames = new ArrayList<>();
        product.getTags().forEach(tag -> tagNames.add(tag.getTagName()));

        Pageable pageable = PageRequest.of(0, QUERY_LIMIT);
        return repository.findByTagsTagNameInAndIsShownOrderByPriceAsc(tagNames, true, pageable);
    }

    public List<Product> getProductsInEventBanner(int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return repository.findByIsInEventAndIsShownOrderByUpdatedAtDesc(true, true, pageable);
    }

    public List<Product> getOnSaleProducts(int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return repository.findByDiscountPercentageGreaterThanAndIsShownOrderByPriceAsc(0.0, true, pageable);
    }

    public List<Product> getLatestProducts(int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        return repository.findByIsShownOrderByCreatedAtDesc(true, pageable);
    }

    public List<Product> getProductsUnderPrice(int page){
        Pageable pageable = PageRequest.of(page, QUERY_LIMIT);
        BigDecimal price = new BigDecimal(SYSTEM_PRICE_BANNER);
        return repository.findByPriceLessThanAndIsShownOrderByPriceAsc(price, true, pageable);
    }

}
