package com.example.aida.service.UsersService;

import com.example.aida.Entities.*;
import com.example.aida.Enums.SubscriptionStatus;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.ProductRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Customer getCustomerInfo(){
        String username = getAuthenticatedUsername();
        return customerRepository.findByEmail(username);
    }

    public Customer updateInfo(Address address, String phone){
        Customer customer = getCustomerInfo();
        if (customer != null) {
            customer.setAddress(address);
            customer.setPhoneNumber(phone);
            customerRepository.save(customer);
        }
        return customer;
    }

    public Customer updateCards(Set<Card> cards){
        Customer customer = getCustomerInfo();
        if (customer != null) {
            customer.setCards(cards);
        }
        return customer;
    }

    public Customer updateSettings(CustomerSettings settings){
        Customer customer = getCustomerInfo();
        if (customer != null) {
            customer.setCustomerSettings(settings);
        }
        return customer;
    }

    public Customer subscribe(@NotNull String product_id){
        Product product = productRepository.findById(product_id).orElse(null);
        if(product == null ){
            return null;
        }
        Customer customer = getCustomerInfo();
        if (customer != null && product.getAllowSubscription()) {
            Subscription subscription = Subscription.builder()
                    .product_id(product_id).build();

            if(product.getQuantity() == 0)
                subscription.setStatus(SubscriptionStatus.OutOfStock);
            else{
                if(product.getDiscount() != null && product.getDiscount().getPercentage() > 0)
                    subscription.setStatus(SubscriptionStatus.OnSale);
                else if(product.getTimeSinceRestocking().isBefore(LocalDate.now().plusDays(3)))//it's been less than three days since the product was restocked
                    subscription.setStatus(SubscriptionStatus.NewShipment);
            }

            if(customer.getSubscriptions() == null)
                customer.setSubscriptions(Set.of(subscription));
            else
                customer.getSubscriptions().add(subscription);
            customerRepository.save(customer);
        }
        product.setSubscribers(product.getSubscribers() + 1);

        System.out.println(saveProductAsync(product));

        return customer;
    }

    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Async
    public CompletableFuture<Product> saveProductAsync(Product product) {
        return CompletableFuture.completedFuture(productRepository.save(product));
    }

}
