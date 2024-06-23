package com.example.aida.service.CartService;

import com.example.aida.Entities.CartItem;
import com.example.aida.Entities.Customer;
import com.example.aida.Entities.Product;
import com.example.aida.Repositories.CartItemRepository;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    public CartItem addProductToCart(String productId, int quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        Product product = productOpt.get();
        //Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        String username = getAuthenticatedUsername();
        Customer customer = customerRepository.findByEmail(username);
        List<CartItem> cart = customer.getCart();
        for (CartItem cartItem : cart) {
            if (cartItem.getProductId().equals(productId)) {
                if(quantity + cartItem.getQuantity() > product.getQuantity()){
                    throw new RuntimeException("Quantity not available");
                }
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
                cartItem.setTotalPrice(cartItem.getTotalPrice()+cartItem.getPrice());
                cartRepository.save(cartItem);
                customer.setCart(cart);
                customerRepository.save(customer);
                return cartItem;
            }
        }
        quantity=1;
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setName(product.getProductName());
        cartItem.setQuantity(quantity);
        cartItem.setCustomerId(customer.getId());
        cartItem.setPrice(product.getPrice() * quantity);
        cartItem.setTotalPrice(cartItem.getTotalPrice()+cartItem.getPrice());
        cartItem = cartRepository.save(cartItem);
        cart.add(cartItem);
        cartItem.setUpdatedAt(new Date());
        customer.setCart(cart);
        customerRepository.save(customer);
        return cartItem;
    }

    public CartItem updateProductQuantity(String productId, int quantity) {
        String username = getAuthenticatedUsername();
        Customer customer = customerRepository.findByEmail(username);
        List<CartItem> cart = customer.getCart();
        for (CartItem cartItem : cart) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(quantity);
                cartItem.setPrice(cartItem.getPrice() * quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice()+cartItem.getPrice());
                cartRepository.save(cartItem);
                customer.setCart(cart);
                customerRepository.save(customer);
                return cartItem;
            }
        }
        throw new RuntimeException("Cart item not found");
    }

    public List<CartItem> getCartDetails() {
        //Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        String username = getAuthenticatedUsername();
        Customer customer = customerRepository.findByEmail(username);
        return customerRepository.findById(customer.getId()).orElseThrow(() -> new RuntimeException("Customer not found")).getCart();
    }
    public void removeProductFromCart(String productId) {
        String username = getAuthenticatedUsername();
        Customer customer = customerRepository.findByEmail(username);
        List<CartItem> cart = customer.getCart();
       for (CartItem cartItem : cart) {
            if (cartItem.getProductId().equals(productId)) {
                cartRepository.delete(cartItem);
                cart.remove(cartItem);
                customer.setCart(cart);
                customerRepository.save(customer);
                return;
            }
        }
        throw new RuntimeException("Cart item not found");
    }

    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }


}
