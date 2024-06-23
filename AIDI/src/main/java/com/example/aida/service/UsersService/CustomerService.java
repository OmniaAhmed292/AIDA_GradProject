package com.example.aida.service.UsersService;

import com.example.aida.Entities.*;
import com.example.aida.Enums.SubscriptionStatus;
import com.example.aida.Repositories.CustomerRepository;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.auth.Authorization;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Authorization authorization;



    public Customer updateInfo(Address address, String phone){
        Customer customer = authorization.getCustomerInfo();
        if (customer != null) {
            customer.setAddress(address);
            customer.setPhoneNumber(phone);
            customerRepository.save(customer);
        }
        return customer;
    }

    public Customer updateCards(Set<Card> cards){
        Customer customer = authorization.getCustomerInfo();
        if (customer != null) {
            customer.setCards(cards);
            customerRepository.save(customer);
        }
        return customer;
    }

    public Customer updateSettings(CustomerSettings settings){
        Customer customer = authorization.getCustomerInfo();
        if (customer != null) {
            customer.setCustomerSettings(settings);
            customerRepository.save(customer);
        }
        return customer;
    }

    public Customer subscribe(@NotNull String product_id){
        Product product = productRepository.findById(product_id).orElse(null);
        if(product == null ){
            return null;
        }
        Customer customer = authorization.getCustomerInfo();
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

    public Customer getCustomerInfo(){
        return authorization.getCustomerInfo();
    }

    @Async
    public CompletableFuture<Product> saveProductAsync(Product product) {
        return CompletableFuture.completedFuture(productRepository.save(product));
    }



}
