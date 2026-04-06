package com.shiv.notification.service.events;

import com.shiv.notification.service.domain.NotificationService;
import com.shiv.notification.service.domain.OrderEventEntity;
import com.shiv.notification.service.domain.OrderEventRepository;
import com.shiv.notification.service.domain.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {
    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);
    private final NotificationService notificationService ;
    private final OrderEventRepository orderEventRepository ;

    public OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository) {
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }

    @RabbitListener(queues = "${notifications.new-orders-queue}")
    void handelOrderCreatedEvent(OrderCreatedEvent event){
        log.info("Order Created Event: {}", event);
        if(orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", event.eventId());
            return ;
        }
        notificationService.sendOrderCreatedNotification(event);
        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent) ;
    }

    @RabbitListener(queues = "${notifications.delivered-orders-queue}")
    void handelOrderDeliveredEvent(OrderDeliveredEvent event){
        System.out.println("Order Created Event: " + event);
        if(orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", event.eventId());
            return ;
        }
        notificationService.sendOrderDeliveredNotification(event);
        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent) ;
    }

    @RabbitListener(queues = "${notifications.cancelled-orders-queue}")
    void handelOrderDeliveredEvent(OrderCancelledEvent event){
        System.out.println("Order Created Event: " + event);
        if(orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", event.eventId());
            return ;
        }
        notificationService.sendOrderCancelledNotification(event);
        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent) ;
    }

    @RabbitListener(queues = "${notifications.error-orders-queue}")
    void handelOrderDeliveredEvent(OrderErrorEvent event){
        System.out.println("Order Created Event: " + event);
        notificationService.sendOrderErrorEventNotification(event);
    }


}
