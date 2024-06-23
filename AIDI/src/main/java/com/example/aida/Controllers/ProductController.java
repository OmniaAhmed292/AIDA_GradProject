package com.example.aida.Controllers;

import com.example.aida.Entities.Product;
import com.example.aida.Entities.Reviews;
import com.example.aida.Entities.Tag;
import com.example.aida.service.ProductService.FileProcessingService;
import com.example.aida.service.ProductService.ProductSearchRequest;
import com.example.aida.service.ProductService.ProductService;
import com.example.aida.service.ReviewService.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FileProcessingService fileProcessingService;
    private final ReviewService reviewService;
    //---------------------------------Tag---------------------------------
    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags(){
        return ResponseEntity.ok(productService.getAllTags());
    }
    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(productService.getTagById(id));
    }
    @PostMapping("/tags")
    public ResponseEntity<Tag> saveTag(@RequestBody Tag tag){
        return ResponseEntity.ok(productService.saveTag(tag));
    }

    //---------------------------------Product---------------------------------
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product)throws IOException {
        return ResponseEntity.ok(productService.save(product, true));
    }
    @PostMapping("/fileSystem")
    public String uploadFile(@RequestBody MultipartFile file) {
        String status = fileProcessingService.uploadFile(file);
        return status;
        //return "CREATED".equals(status) ? new ResponseEntity<>(HttpStatus.CREATED) : ("EXIST".equals(status) ? new ResponseEntity<>(HttpStatus.NOT_MODIFIED) : new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }
    @PostMapping("/fileSystem/{productId}")
    public String uploadImage(@PathVariable String productId,@RequestBody MultipartFile file) {
        String status = productService.saveProductImage(productId, file);
        return status;
        //return "CREATED".equals(status) ? new ResponseEntity<>(HttpStatus.CREATED) : ("EXIST".equals(status) ? new ResponseEntity<>(HttpStatus.NOT_MODIFIED) : new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }
    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData= fileProcessingService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.findAll());
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") String id) {
        productService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "id") String id){
        Product product = productService.getProductById(id, false);
        product.setSales(0);
        product.setSubscribers(0);
        product.setViews(0);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}/vendor-view")
    public ResponseEntity<Product> getProductByIdVendorView(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(productService.getProductById(id, true));
    }

    @GetMapping("/{category}/{page}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable(name = "category") String category,
                                                              @PathVariable(name = "page") int page){
        return ResponseEntity.ok(productService.getProductByCategory(category,page));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Product>> getProductsOfVendor(@PathVariable(name = "vendorId") String vendorId){
        return ResponseEntity.ok(productService.getProductsOfVendor(vendorId));
    }

    @GetMapping("/similar/{productId}")
    public ResponseEntity<List<Product>> getSimilarProducts(@PathVariable(name = "productId") String productId){
        return ResponseEntity.ok(productService.getSimilarProducts(productId));
    }

    @GetMapping("/event-banner/{page}")
    public ResponseEntity<List<Product>> getProductsInEventBanner(@PathVariable(name = "page") int page){
        return ResponseEntity.ok(productService.getProductsInEventBanner(page));
    }

    //TODO: Add the missing ProductSearchRequest class
    @PostMapping("/search")
    public ResponseEntity<List<Product>> productSearch(@RequestBody ProductSearchRequest request){
        return ResponseEntity.ok(productService.productSearch(request.getSearch(), request.getMinRating(), request.getMinPrice(),
                request.getMaxPrice(), request.getAvailable(), request.getIs_used(), request.getSortFeild(), request.getDiscount(), request.getPage()));
    }

    @GetMapping("/on-sale/{page}")
    public ResponseEntity<List<Product>> getOnSaleProducts(@PathVariable(name = "page") int page){
        return ResponseEntity.ok(productService.getOnSaleProducts(page));
    }

    @GetMapping("/latest/{page}")
    public ResponseEntity<List<Product>> getLatestProducts(@PathVariable(name = "page") int page){
        return ResponseEntity.ok(productService.getLatestProducts(page));
    }

    @GetMapping("/price-under/{page}")
    public ResponseEntity<List<Product>> getProductsForPricesUnder(@PathVariable(name = "page") int page){
        return ResponseEntity.ok(productService.getProductsUnderPrice(page));
    }

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<Reviews> createReview(
            @PathVariable String productId,
            @RequestBody Reviews review) {
        Reviews createdReview = reviewService.createReview(productId, review);
        return ResponseEntity.ok(createdReview);
    }


    @GetMapping("/{userId}/reviews")
    public ResponseEntity<List<Reviews>> getReviewsByUserId(@PathVariable String userId) {
        List<Reviews> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable String productId,
            @PathVariable String reviewId) {
        reviewService.deleteReview(productId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<Reviews> getReviewById(
            @PathVariable String productId,
            @PathVariable String reviewId) {
        Reviews review = reviewService.getReviewById(productId, reviewId);
        return ResponseEntity.ok(review);
    }
}
