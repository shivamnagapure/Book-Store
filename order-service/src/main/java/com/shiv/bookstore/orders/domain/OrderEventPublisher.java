package com.shiv.bookstore.orders.domain;

import com.shiv.bookstore.orders.domain.models.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    public void publish(OrderCreatedEvent event){

    }
}
