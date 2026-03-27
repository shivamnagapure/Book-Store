package com.shiv.bookstore.orders.domain.models;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;

@Builder
public record OrderDTO(
        String orderNumber,
        String user,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        OrderStatus status,
        String comments,
        LocalDateTime createdAt) {}
