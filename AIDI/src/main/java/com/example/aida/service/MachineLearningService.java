package com.example.aida.service;

import com.example.aida.Entities.Customer;
import com.example.aida.Entities.Product;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.auth.Authorization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MachineLearningService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    Authorization authorization;

    // send a request to the machine learning model to get the prediction
    public List<Product> getPrediction() {

        Customer customer = authorization.getCustomerInfo();
        final String uri = "http://localhost:9999/recommend?user_id=" + customer.getId();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        String result = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        try {
            map = mapper.readValue(result, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String, Map<String, String>> productsMap = (Map<String, Map<String, String>>) map.get("products");
        List<String> recommendedProducts = new ArrayList<>();
        for (Map<String, String> productEntry : productsMap.values()) {
            recommendedProducts.add(productEntry.get("product"));
        }

        return retrieveProducts(recommendedProducts);
    }

    //retrieve recommended products from database
    private List<Product> retrieveProducts(List<String> recommendedProducts) {
        List<Product> products = new ArrayList<>();
        for (String productId : recommendedProducts) {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                products.add(product);
            }
        }
        return products;
    }
}
