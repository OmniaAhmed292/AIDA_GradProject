package com.example.aida.service.ProductService;

import com.example.aida.Entities.Product;
import com.example.aida.Entities.ProductImage;
import com.example.aida.Entities.Tag;
import com.example.aida.Entities.Vendor;
import com.example.aida.Enums.SortFeild;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.Repositories.ProductRepositoryImpl;
import com.example.aida.Repositories.TagRepository;
import com.example.aida.Repositories.VendorRepository;
import com.example.aida.auth.Authorization;
import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepositoryImpl searchRep;
    private final ProductRepository repository;
    private final TagRepository tagRepository;
    private final VendorRepository vendorRepository;
    private final FileProcessingServiceImpl imageUploadService;

    @Autowired
    private Authorization authorization;

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
    public Product getProductById(@NonNull String id, Boolean isVendor){
        if(isVendor) {
            Vendor vendor = authorization.getVendorInfo();
            Product product = repository.findById(id).orElse(null);

            if(product == null || vendor == null || !product.getVendorId().equals(vendor.getId())){
                return null;
            }

            return product;
        }
        Product product = repository.findById(id).orElse(null);
        product.setViews(product.getViews() + 1);
        saveProductAsync(product);
        return product;
    }
  
  public String saveProductImage(String productId, MultipartFile file) {
        Product product = repository.findById(productId).orElse(null);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        String imageUrl = imageUploadService.uploadFile(file);
            Set<ProductImage> images = product.getImages();
            ProductImage productImage = new ProductImage();
            productImage.setFilePath(imageUrl);
            images.add(productImage);
            repository.save(product);
            return imageUrl;
    }

    public Product save(Product product, Boolean isVendor) throws IOException {
        if(isVendor) { //if vendor check if the product belongs to the vendor
            Vendor vendor = authorization.getVendorInfo();

            if(product.get_id() == null) { //this is a create operation
                product.setVendorId(vendor.getId());
                product.setIsShown(true);
                product.setSubscribers(0);
                product.setViews(0);
                product.setSales(0);
                product.setRevenue(0.0);

                if (product.getQuantity() != 0)
                    product.setTimeSinceRestocking(LocalDate.now());

                return repository.save(product);
            }
            else {
                //this is an update operation
                Product oldProduct = repository.findById(product.get_id()).orElse(null);
                //check if the product exists and belongs to the vendor
                if(oldProduct == null ){
                    throw new RuntimeException("Product not found in the database");
                } else if (!Objects.equals(oldProduct.getVendorId(), vendor.getId())){
                    throw new RuntimeException("Product does not belong to the vendor");
                }

                oldProduct.setProductName(product.getProductName());
                oldProduct.setDescription(product.getDescription());
                oldProduct.setPrice(product.getPrice());
                oldProduct.setTaxes(product.getTaxes());
                oldProduct.setCategoryName(product.getCategoryName());

                oldProduct.setAllowSubscription(product.getAllowSubscription());
                oldProduct.setIsUsed(product.getIsUsed());
                oldProduct.setSpecifications(product.getSpecifications());
                oldProduct.setTags(product.getTags());
                oldProduct.setDiscount(product.getDiscount());
                //set time since restocking
                if(product.getQuantity() > 0 && oldProduct.getQuantity() == 0) {
                    oldProduct.setTimeSinceRestocking(LocalDate.now());
                    notifyRestock(oldProduct.get_id());
                }
                oldProduct.setQuantity(product.getQuantity());
                return repository.save(oldProduct);
            }
        }
        else { //this is an update operation
            //save the image
//            Set<ProductImage> images = oldProduct.getImages();
//            //String imageUrl = imageUploadService.uploadImage(file);
//            ProductImage productImage = new ProductImage();
//            productImage.setFilePath(imageUrl);
//            images.add(productImage);
            return repository.save(product);
        }

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
        Pageable pageable = PageRequest.of(page-1, QUERY_LIMIT); // page is 1 indexed
        return searchRep.productSearch(search, minRating, minPrice, maxPrice, available, is_used, sortFeild, discount, pageable);
    }

    public List<Product> getProductByCategory(String categoryName,int page){
        Pageable pageable = PageRequest.of(page-1, QUERY_LIMIT); // page is 1 indexed
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
        Pageable pageable = PageRequest.of(page-1, QUERY_LIMIT);
        return repository.findByIsInEventAndIsShownOrderByUpdatedAtDesc(true, true, pageable);
    }

    public List<Product> getOnSaleProducts(int page){
        Pageable pageable = PageRequest.of(page-1, QUERY_LIMIT);
        return repository.findByDiscountPercentageGreaterThanAndIsShownOrderByPriceAsc(0.0, true, pageable);
    }

    public List<Product> getLatestProducts(int page){
        Pageable pageable = PageRequest.of(page-1, QUERY_LIMIT);
        return repository.findByIsShownOrderByCreatedAtDesc(true, pageable);
    }

    public List<Product> getProductsUnderPrice(int page){
        Pageable pageable = PageRequest.of(page-1, QUERY_LIMIT);
        Double price = (double) SYSTEM_PRICE_BANNER;
        return repository.findByPriceLessThanAndIsShownOrderByPriceAsc(price, true, pageable);
    }


    @Async
    public void notifyRestock(String product_id){
        //TODO implement subscription restock notification
    }

    @Async
    public CompletableFuture<Product> saveProductAsync(Product product) {
        return CompletableFuture.completedFuture(repository.save(product));
    }

}
