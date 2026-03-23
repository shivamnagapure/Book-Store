package com.shiv.bookstore.orders.domain.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record CreateOrderRequest(
        @Valid @NotBlank() Set<OrderItem> items, @Valid Customer customer, @Valid Address deliveryAddress) {}

// @Valid is used for nested object validation. In CreateOrderRequest, we use it on Customer because it’s a nested
// object.
// Inside Customer, we don’t need @Valid because it only contains primitive fields with their own validation annotations
