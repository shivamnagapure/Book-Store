package com.shiv.bookstore.orders.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiv.bookstore.orders.domain.models.OrderCreatedEvent;
import com.shiv.bookstore.orders.domain.models.OrderEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderEventService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventService.class);

    private final ObjectMapper objectMapper ;
    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisher orderEventPublisher ;

   OrderEventService(
           ObjectMapper objectMapper ,
           OrderEventRepository orderEventRepository,
           OrderEventPublisher orderEventPublisher
           ){
       this.objectMapper = objectMapper ;
       this.orderEventRepository = orderEventRepository ;
       this.orderEventPublisher = orderEventPublisher ;
   }


    void save(OrderCreatedEvent event){
        //convert event data to OrderEventEntity
        OrderEventEntity orderEvent = OrderEventEntity.builder()
                .eventId(event.eventId())
                .eventType(OrderEventType.ORDER_CREATED)
                .orderNumber(event.orderNumber())
                .createdAt(event.createdAt())
                .payload(toJsonPayload(event))
                .build();

        this.orderEventRepository.save(orderEvent);

    }

    private String toJsonPayload(Object object) {
       try {
           return objectMapper.writeValueAsString(object);
       } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
       }

    }

    //logic may be changed according to requirement
    public void publishOrderEvents() {
        //get All events to publish
        Sort sort = Sort.by("createdAt").ascending();
        List<OrderEventEntity> events = orderEventRepository.findAll(sort);
        log.info("Found {} Order Events to be published", events.size());
        for (OrderEventEntity event : events){
            this.publishEvent(event);
            orderEventRepository.delete(event); //delete event after publishing
        }
    }

    private void publishEvent(OrderEventEntity event){
        OrderEventType eventType = event.getEventType() ;
        switch (eventType){
            case ORDER_CREATED :
                OrderCreatedEvent orderCreatedEvent = fromJsonPayload(event.getPayload() , OrderCreatedEvent.class);
                orderEventPublisher.publish(orderCreatedEvent);

            default:
                log.warn("Unsupported OrderEventType: {}", eventType);
        }
    }

    private <T> T fromJsonPayload(String json, Class<T> type){
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
