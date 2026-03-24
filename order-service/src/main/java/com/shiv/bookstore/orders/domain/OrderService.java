package com.shiv.bookstore.orders.domain;

import com.shiv.bookstore.orders.domain.models.CreateOrderRequest;
import com.shiv.bookstore.orders.domain.models.CreateOrderResponse;
import com.shiv.bookstore.orders.domain.models.OrderCreatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEventService orderEventService ;

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator ,OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService ;
    }

    //    public String findOrders(String userName) {
    //        return  ;
    //    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        orderValidator.validate(request); // validating products
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(userName);
        OrderEntity saveOrder = this.orderRepository.save(newOrder);

        //converting entity to record to publish in RabbitMQ
        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(saveOrder);
        orderEventService.save(orderCreatedEvent);

        return new CreateOrderResponse(saveOrder.getOrderNumber());
    }
}
