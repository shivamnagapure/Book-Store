package com.shiv.bookstore.orders.clients.catalog;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class ProductServiceClient {

    RestClient restClient ;

    ProductServiceClient(RestClient restClient){
        this.restClient = restClient ;
    }

    public Optional<Product> getProductByCode(String code){
        var product = restClient.
                get()
                .uri("/api/products/{code}" , code)
                .retrieve()
                .body(Product.class); // Converts JSON response to Product class

        return Optional.ofNullable(product);
    }
}
