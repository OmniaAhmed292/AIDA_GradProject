package com.example.aida.Controllers;

import com.example.aida.Entities.Product;
import com.example.aida.Entities.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.aida.service.ProductService.ProductService;
import com.example.aida.service.ProductService.ProductSearchRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

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
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.save(product));
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
        return ResponseEntity.ok(productService.getProductById(id));
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




}
