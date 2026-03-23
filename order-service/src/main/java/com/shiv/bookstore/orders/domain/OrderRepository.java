package com.shiv.bookstore.orders.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
        extends JpaRepository<
                OrderEntity, Long> { // this is and root repository , handles all operation related to order
}
