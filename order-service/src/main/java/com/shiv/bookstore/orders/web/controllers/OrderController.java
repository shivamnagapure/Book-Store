package com.shiv.bookstore.orders.web.controllers;

import com.shiv.bookstore.orders.domain.OrderNotFoundException;
import com.shiv.bookstore.orders.domain.OrderService;
import com.shiv.bookstore.orders.domain.SecurityService;
import com.shiv.bookstore.orders.domain.models.CreateOrderRequest;
import com.shiv.bookstore.orders.domain.models.CreateOrderResponse;
import com.shiv.bookstore.orders.domain.models.OrderDTO;
import com.shiv.bookstore.orders.domain.models.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    // Validating CreateOrder request by @Valid annotating
    // It ensures that all constraints defined in the DTO are validated before the request is processed.
    // f validation fails, Spring throws a MethodArgumentNotValidException, preventing invalid data from reaching the
    // business logic.
    // It also supports cascading validation for nested objects.

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoginUserName();
        return orderService.createOrder(userName, request);
    }

    @GetMapping
    List<OrderSummary> getOrders() {
        String userName = securityService.getLoginUserName();
        return orderService.findOrders(userName);
    }

    @GetMapping("/{orderNumber}")
    OrderDTO getOrder(@PathVariable String orderNumber) {
        String userName = securityService.getLoginUserName();
        return orderService
                .findUserOrder(userName, orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
