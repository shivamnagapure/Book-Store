package com.shiv.bookstore.orders.domain.models;

public record OrderSummary(
        String orderNumber, OrderStatus status
) {}
