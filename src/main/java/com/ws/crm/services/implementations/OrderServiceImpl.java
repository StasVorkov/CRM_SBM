package com.ws.crm.services.implementations;

import com.ws.crm.authentication.AuthenticationFacade;
import com.ws.crm.exceptions.ResourceNotFoundException;
import com.ws.crm.models.Order;
import com.ws.crm.models.User;
import com.ws.crm.models.enums.Status;
import com.ws.crm.repositories.OrdersRepository;
import com.ws.crm.services.LogService;
import com.ws.crm.services.OrderService;
import com.ws.crm.services.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j
public class OrderServiceImpl implements OrderService {

    private final LogService logService;
    private final OrdersRepository ordersRepository;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacadeImpl;


    @Autowired
    public OrderServiceImpl(LogService logService,
                            OrdersRepository ordersRepository,
                            UserService userService,
                            AuthenticationFacade authenticationFacadeImpl) {
        this.logService = logService;
        this.ordersRepository = ordersRepository;
        this.userService = userService;
        this.authenticationFacadeImpl = authenticationFacadeImpl;
    }

    @Override
    @Transactional
    public List<Order> getAllOrdersDeep() {
        List<Order> ordersFirstStep = ordersRepository.findOrdersFirstStep();
        List<Order> ordersSecondStep = ordersRepository.findOrdersSecondStep(ordersFirstStep);
        return ordersSecondStep;
    }

    @Override
    public List<Order> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public Order getOrderById(int id) throws ServiceException {
        try {
            return ordersRepository.findOrderByIdCustom(id).orElseThrow(ResourceNotFoundException::new);
        } catch (ResourceNotFoundException e) {
            log.error("Order not found");
            throw new ServiceException("Service Error. Receiving Order by Id failed. Order not found", e);
        }
    }

    @Override
    @Transactional
    public Order save(Order order) {
        Order newOrder = enrichOrder(order);
        ordersRepository.save(newOrder);
        logService.createFirstLogByOrder(newOrder);
        return newOrder;
    }

    private Order enrichOrder(Order order) throws ServiceException {
        try {
            order.setOrderDate(LocalDate.now());
            order.setStatus(Status.NEW);
                order.setUser(getUserFromAuthentication());
        } catch (ServiceException e) {
            log.error("User not found");
            throw new ServiceException("Service Error. Enriching of Order failed. Order have not saved, because User not found", e);
        }
        return order;
    }

    private User getUserFromAuthentication() throws ServiceException{
        Authentication authentication = authenticationFacadeImpl.getAuthentication();
        String userName = authentication.getName();
        return userService.getUserByEmail(userName);
    }

    @Override
    @Transactional
    public Order update(Order order) {
        ordersRepository.save(order);
        logService.createNextLogByOrder(order);
        return order;
    }

    @Override
    public List<Order> getAllOrdersByUser(User authUser) {
        return ordersRepository.findAllByUser(authUser.getId());
    }

    @Override
    public void deleteOrder(int id) {
        ordersRepository.deleteById(id);
    }
}
