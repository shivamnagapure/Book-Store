package com.shiv.bookstore.orders.domain.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record OrderItem(
        @NotBlank(message = "Code is required") String code,
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Price is required") BigDecimal price,
        @NotBlank(message = "Quantity is required") @Min(value = 1, message = "Min quantity must be 1")
                Integer quantity) {}
