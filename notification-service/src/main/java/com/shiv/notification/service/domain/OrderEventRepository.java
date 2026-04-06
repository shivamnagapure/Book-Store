package com.shiv.notification.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface OrderEventRepository extends JpaRepository<OrderEventEntity , Long> {

    boolean existsByEventId(String eventId);

}
