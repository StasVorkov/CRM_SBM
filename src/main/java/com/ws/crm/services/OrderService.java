package com.ws.crm.services;


import com.ws.crm.models.Order;
import com.ws.crm.models.User;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrdersDeep();

    List<Order> getAllOrders();

    Order getOrderById(int id);

    Order save(Order newOrder);

    void deleteOrder(int id);

    Order update(Order order);

    List<Order> getAllOrdersByUser(User authUser);
}
