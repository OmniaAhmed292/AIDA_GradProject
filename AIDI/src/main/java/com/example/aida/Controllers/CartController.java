package com.example.aida.Controllers;

import com.example.aida.Entities.CartItem;
import com.example.aida.Repositories.CartItemRepository;
import com.example.aida.service.CartService.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartItemRepository cartRepository;

    @PostMapping("/add/product/{productId}/quantity/{quantity}")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable String productId, @PathVariable int quantity) {
        CartItem cart = cartService.addProductToCart(productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> updateProductQuantity(@RequestParam String productId, @RequestParam int quantity) {
        CartItem cart = cartService.updateProductQuantity(productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeProductFromCart(@RequestParam String productId) {
        cartService.removeProductFromCart(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details")
    public ResponseEntity<List<CartItem>> getCartDetails() {
        List<CartItem> cartDetails = cartService.getCartDetails();
        return ResponseEntity.ok(cartDetails);
    }
}
