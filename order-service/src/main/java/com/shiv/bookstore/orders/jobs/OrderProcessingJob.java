package com.shiv.bookstore.orders.jobs;

import com.shiv.bookstore.orders.domain.OrderService;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
   Create Order → save as NEW

   Later:
   Job → process → update status → create event

   2 JOBS:
   1️⃣ OrderProcessingJob
   -> Handles business logic
    NEW → DELIVERED / CANCELLED / ERROR
    Creates event in DB

    2️⃣ OrderEventsPublishingJob

     -> Handles message publishing
       Reads outbox table
       Sends to RabbitMQ
*/
@Component
public class OrderProcessingJob {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessingJob.class);

    private final OrderService orderService;

    public OrderProcessingJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "${orders.new-orders-job-cron}")
    @SchedulerLock(name = "processNewOrders")
    public void processNewOrders() {
        LockAssert.assertLocked();
        orderService.processOrders();
    }
}
