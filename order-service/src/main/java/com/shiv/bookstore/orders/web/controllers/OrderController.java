package com.shiv.bookstore.orders.web.controllers;

import com.shiv.bookstore.orders.domain.OrderService;
import com.shiv.bookstore.orders.domain.SecurityService;
import com.shiv.bookstore.orders.domain.models.CreateOrderRequest;
import com.shiv.bookstore.orders.domain.models.CreateOrderResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService ;
    private final SecurityService securityService ;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request){
        String userName = securityService.getLoginUserName();
        return orderService.findOrders(userName);
    }
}
