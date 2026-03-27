package com.shiv.bookstore.orders.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiv.bookstore.orders.domain.models.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderEventService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventService.class);

    private final ObjectMapper objectMapper;
    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisher orderEventPublisher;

    OrderEventService(
            ObjectMapper objectMapper,
            OrderEventRepository orderEventRepository,
            OrderEventPublisher orderEventPublisher) {
        this.objectMapper = objectMapper;
        this.orderEventRepository = orderEventRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    void save(OrderCreatedEvent event) {
        // convert event data to OrderEventEntity
        OrderEventEntity orderEvent = OrderEventEntity.builder()
                .eventId(event.eventId())
                .eventType(OrderEventType.ORDER_CREATED)
                .orderNumber(event.orderNumber())
                .createdAt(event.createdAt())
                .payload(toJsonPayload(event))
                .build();

        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderDeliveredEvent event) {
        // convert event data to OrderEventEntity
        OrderEventEntity orderEvent = OrderEventEntity.builder()
                .eventId(event.eventId())
                .eventType(OrderEventType.ORDER_DELIVERED)
                .orderNumber(event.orderNumber())
                .createdAt(event.createdAt())
                .payload(toJsonPayload(event))
                .build();

        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderCancelledEvent event) {
        // convert event data to OrderEventEntity
        OrderEventEntity orderEvent = OrderEventEntity.builder()
                .eventId(event.eventId())
                .eventType(OrderEventType.ORDER_DELIVERED)
                .orderNumber(event.orderNumber())
                .createdAt(event.createdAt())
                .payload(toJsonPayload(event))
                .build();

        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderErrorEvent event) {
        // convert event data to OrderEventEntity
        OrderEventEntity orderEvent = OrderEventEntity.builder()
                .eventId(event.eventId())
                .eventType(OrderEventType.ORDER_DELIVERED)
                .orderNumber(event.orderNumber())
                .createdAt(event.createdAt())
                .payload(toJsonPayload(event))
                .build();

        this.orderEventRepository.save(orderEvent);
    }

    // logic may be changed according to requirement
    public void publishOrderEvents() {
        // get All events to publish
        Sort sort = Sort.by("createdAt").ascending();
        List<OrderEventEntity> events = orderEventRepository.findAll(sort);
        log.info("Found {} Order Events to be published", events.size());
        for (OrderEventEntity event : events) {
            this.publishEvent(event);
            orderEventRepository.delete(event); // delete event after publishing
        }
    }

    private void publishEvent(OrderEventEntity event) {
        OrderEventType eventType = event.getEventType();
        switch (eventType) {
            case ORDER_CREATED:
                OrderCreatedEvent orderCreatedEvent = fromJsonPayload(event.getPayload(), OrderCreatedEvent.class);
                orderEventPublisher.publish(orderCreatedEvent);
                break;

            case ORDER_DELIVERED:
                OrderDeliveredEvent orderDeliveredEvent =
                        fromJsonPayload(event.getPayload(), OrderDeliveredEvent.class);
                orderEventPublisher.publish(orderDeliveredEvent);
                break;

            case ORDER_CANCELLED:
                OrderCancelledEvent orderCancelledEvent =
                        fromJsonPayload(event.getPayload(), OrderCancelledEvent.class);
                orderEventPublisher.publish(orderCancelledEvent);
                break;

            case ORDER_PROCESSING_FAILED:
                OrderErrorEvent orderErrorEvent = fromJsonPayload(event.getPayload(), OrderErrorEvent.class);
                orderEventPublisher.publish(orderErrorEvent);
                break;

            default:
                log.warn("Unsupported OrderEventType: {}", eventType);
        }
    }

    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJsonPayload(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
