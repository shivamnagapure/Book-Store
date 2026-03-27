package com.shiv.bookstore.orders.domain;

import com.shiv.bookstore.orders.domain.models.OrderStatus;
import java.util.List;
import java.util.Optional;

import com.shiv.bookstore.orders.domain.models.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository
        extends JpaRepository<
                OrderEntity, Long> { // this is and root repository , handles all operation related to order

    List<OrderEntity> findByStatus(OrderStatus status);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus status) {
        OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(status);
        this.save(order);
    }

    @Query("""
        select new com.shiv.bookstore.orders.domain.models.OrderSummary(o.orderNumber , o.status)
            from OrderEntity o
                where o.userName = :userName
    """)
    List<OrderSummary> findByUserName(String userName);

    //Solve N + 1 Query Problems
    @Query("""
        select distinct o
            from OrderEntity o
                left join fetch o.items
                    where o.orderNumber = :orderNumber and o.userName = :userName
    """)
    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
