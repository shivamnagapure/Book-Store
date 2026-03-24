package com.shiv.bookstore.orders.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

// event data which have to publish to RabbitMQ , and notification-service consume it
public record OrderCreatedEvent(
        String eventId,
        String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        LocalDateTime createdAt) {}
