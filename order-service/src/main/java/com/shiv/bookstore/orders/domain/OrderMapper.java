package com.shiv.bookstore.orders.domain;

import com.shiv.bookstore.orders.domain.models.CreateOrderRequest;
import com.shiv.bookstore.orders.domain.models.OrderItem;
import com.shiv.bookstore.orders.domain.models.OrderStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class OrderMapper {

    static OrderEntity convertToEntity(CreateOrderRequest request){
        OrderEntity newOrder = OrderEntity.builder()
                .orderNumber(UUID.randomUUID().toString())
                .status(OrderStatus.NEW)
                .customer(request.customer())
                .deliveryAddress(request.deliveryAddress())
                .build();

        Set<OrderItemEntity> orderItems = new HashSet<>();

        for(OrderItem item : request.items()){
            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .code(item.code())
                    .name(item.name())
                    .price(item.price())
                    .quantity(item.quantity())
                    .order(newOrder)
                    .build();

            orderItems.add(orderItem);
        }

        newOrder.setItems(orderItems);
        return newOrder ;

    }
}
