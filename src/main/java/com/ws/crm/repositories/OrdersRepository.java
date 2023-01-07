package com.ws.crm.repositories;

import com.ws.crm.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT o FROM Order o  LEFT JOIN FETCH o.project LEFT JOIN FETCH o.items LEFT JOIN FETCH o.user")
    List<Order> findOrdersFirstStep();

    @Query(value = "SELECT o FROM Order o  LEFT JOIN FETCH o.logs WHERE o in :orders ORDER BY o.id")
    List<Order> findOrdersSecondStep(List<Order> orders);

    @Query(value = "SELECT o FROM Order o LEFT JOIN FETCH o.project LEFT JOIN FETCH o.items LEFT JOIN FETCH o.user WHERE o.id =?1")
    Optional<Order> findOrderByIdCustom(int id);

    @Query(value = "SELECT o.order_id, o.name, o.order_date, o.requirement_delivery_date, " +
            "o.estimated_delivery_date, o.actual_delivery_date, o.status, o.project_id, o.user_id FROM ORDERS AS o " +
            "JOIN USERS AS u " +
            "ON o.user_id = u.user_id AND o.user_id=?1 ORDER BY o.order_id",
            nativeQuery = true)
    List<Order> findAllByUser(int id);
}
