package com.ws.crm.services;

import com.ws.crm.models.Log;
import com.ws.crm.models.Order;

import java.util.List;

public interface LogService {

    Log createFirstLogByOrder(Order order);
    Log createNextLogByOrder(Order order);
    List<Log> getLogsByOrderId(int order);
}
