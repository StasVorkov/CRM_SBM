package com.ws.crm.util.orders;

import com.ws.crm.models.Order;
import com.ws.crm.models.enums.Status;
import com.ws.crm.util.projects.UtilProject;
import com.ws.crm.util.users.UtilUser;

import java.time.LocalDate;
import java.util.ArrayList;

public class UtilOrder {

    private static final String NAME = "stubOrder";
    private static volatile Order instance;
    private static final int id = 1;

    private UtilOrder() {
    }

    public static Order get() {
        if (instance == null) {
            synchronized (UtilOrder.class) {
                if (instance == null) {
                    instance = createStub(id);
                }
            }
        }
        return instance;
    }

    public static Order getNew(int i) {
        return createStub(i);
    }

    private static Order createStub(int i) {
        Order stubOrder = new Order();
        stubOrder.setId(i);
        stubOrder.setName(NAME + i);
        stubOrder.setOrderDate(LocalDate.EPOCH);
        stubOrder.setRequirementDeliveryDate(LocalDate.now().plusYears(100));
        stubOrder.setStatus(Status.NEW);
        stubOrder.setUser(UtilUser.get());
        stubOrder.setItems(new ArrayList<>());
        stubOrder.setProject(UtilProject.get());
        stubOrder.setLogs(new ArrayList<>());
        return stubOrder;
    }
}
