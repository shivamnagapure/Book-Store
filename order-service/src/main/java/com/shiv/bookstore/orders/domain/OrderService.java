package com.shiv.bookstore.orders.domain;

import com.shiv.bookstore.orders.domain.models.CreateOrderRequest;
import com.shiv.bookstore.orders.domain.models.CreateOrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //    public String findOrders(String userName) {
    //        return  ;
    //    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(userName);
        OrderEntity saveOrder = this.orderRepository.save(newOrder);
        return new CreateOrderResponse(saveOrder.getOrderNumber());
    }
}
