package com.shiv.bookstore.orders.domain;

import com.shiv.bookstore.orders.clients.catalog.Product;
import com.shiv.bookstore.orders.clients.catalog.ProductServiceClient;
import com.shiv.bookstore.orders.domain.models.CreateOrderRequest;
import com.shiv.bookstore.orders.domain.models.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderValidator {

    private final ProductServiceClient client ;

    OrderValidator(ProductServiceClient client){
        this.client = client;
    }

    void validate(CreateOrderRequest request){
        Set<OrderItem> items = request.items() ;
        for(OrderItem item : items ){
            Product product = client.getProductByCode(item.code()).
                    orElseThrow(() -> new InvalidOrderException("Invalid Product Code"));

            if(item.price().compareTo(product.price()) != 0){
                throw new InvalidOrderException("Product Price Not Matching");
            }
        }

    }
}
