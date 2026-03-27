package com.shiv.bookstore.orders.clients.catalog;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {

    RestClient restClient;

    ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // Handle failure case : if catalog-service is down
    // using try catch -> if catalog-service is down , return Null , so that it will handle in OrderValidator

    // Retry not work with try-catch , because it catches exception , so no exception throw outside method
    // Resilience4j doesn't see any failure , So it does NOT retry

    // Without fallback → your API throws error
    // With fallback → your API responds gracefully

    // name = "catalog-service" -> we can call/pronunce it as backend name
    @CircuitBreaker(name = "catalog-service")
    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallback")
    public Optional<Product> getProductByCode(String code) {
        System.out.println("Fetching Product For Code : " + code);
        var product =
                restClient.get().uri("/api/products/{code}", code).retrieve().body(Product.class);
        return Optional.ofNullable(product); // Converts JSON response to Product class
    }

    Optional<Product> getProductByCodeFallback(String code, Throwable t) {
        System.out.println("ProductServiceClient.getProductByCodeFallback: code: " + code);
        return Optional.empty();
    }
}
