package com.shiv.bookstore.orders.domain;

import com.shiv.bookstore.orders.domain.models.CreateOrderRequest;
import com.shiv.bookstore.orders.domain.models.OrderDTO;
import com.shiv.bookstore.orders.domain.models.OrderItem;
import com.shiv.bookstore.orders.domain.models.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderMapper {

    static OrderEntity convertToEntity(CreateOrderRequest request) {
        OrderEntity newOrder = OrderEntity.builder()
                .orderNumber(UUID.randomUUID().toString())
                .status(OrderStatus.NEW)
                .customer(request.customer())
                .deliveryAddress(request.deliveryAddress())
                .build();

        Set<OrderItemEntity> orderItems = new HashSet<>();

        for (OrderItem item : request.items()) {
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
        return newOrder;
    }

    static OrderDTO convertToDTO(OrderEntity order) {
        Set<OrderItem> orderItems = order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());
        return OrderDTO.builder()
                .orderNumber(order.getOrderNumber())
                .deliveryAddress(order.getDeliveryAddress())
                .items(orderItems)
                .status(order.getStatus())
                .comments(order.getComments())
                .customer(order.getCustomer())
                .createdAt(order.getCreatedAt())
                .build();
    }
    //
    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    //    public BigDecimal getTotalAmount() {
    //        return items.stream() Stream<Orderltem>
    // .map(item -> item.price().multiply(BigDecimal. value0f(item.quantity()))
    //                .reduce (BigDecimal.ZERO, BigDecimal :: add);
}
