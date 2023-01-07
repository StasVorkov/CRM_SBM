package com.ws.crm.service.order;

import com.ws.crm.authentication.implementations.AuthenticationFacadeImpl;
import com.ws.crm.exceptions.ResourceNotFoundException;
import com.ws.crm.models.Order;
import com.ws.crm.repositories.OrdersRepository;

import com.ws.crm.services.LogService;
import com.ws.crm.services.UserService;
import com.ws.crm.services.implementations.OrderServiceImpl;
import com.ws.crm.util.orders.UtilOrder;
import com.ws.crm.util.users.UtilUser;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final int ID = 1;
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private AuthenticationFacadeImpl authenticationFacade;
    @Mock
    private Authentication authentication;
    @Mock
    private UserService userService;
    @Mock
    private LogService logService;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testGetOrderHappyPath() {

        when(ordersRepository.findOrderByIdCustom(ID)).thenReturn(Optional.of(UtilOrder.get()));

        Order newOrderGet = orderService.getOrderById(ID);

        assertEquals(newOrderGet, UtilOrder.get());
    }

    @Test
    public void testGetOrderByIdNegativePath() {
        when(ordersRepository.findOrderByIdCustom(ID)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> orderService.getOrderById(ID));

        verify(ordersRepository,times(1)).findOrderByIdCustom(ID);
    }

    @Test
    public void testAddOrderHappyPath() {

        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(UtilUser.get().getName());
        when(userService.getUserByEmail(anyString())).thenReturn(UtilUser.get());
        when(ordersRepository.save(UtilOrder.get())).thenReturn(UtilOrder.get());

        Order newOrderSave = orderService.save(UtilOrder.get());

        assertEquals(UtilOrder.get().getId(), newOrderSave.getId());
        assertEquals(UtilOrder.get().getName(), newOrderSave.getName());
        assertEquals(UtilOrder.get().getRequirementDeliveryDate(), newOrderSave.getRequirementDeliveryDate());
        assertEquals(UtilOrder.get().getUser(), newOrderSave.getUser());
        assertEquals(UtilOrder.get().getProject(), newOrderSave.getProject());
        assertEquals(UtilOrder.get().getStatus(), newOrderSave.getStatus());

        verify(ordersRepository, times(1)).save(UtilOrder.get());
        verify(logService,times(1)).createFirstLogByOrder(any());
        verify(ordersRepository,times(1)).save(any());
        verify(userService,times(1)).getUserByEmail(any());
    }

    @Test
    public void testAddOrderNegativePath() {
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(UtilUser.get().getName());
        when(userService.getUserByEmail(any())).
                thenThrow(new ServiceException("Service Error. Receiving User by email failed. User not found"));

        Assertions.assertThrows(ServiceException.class, () -> orderService.save(UtilOrder.get()));
    }

    @Test
    public void testGetAllOrdersByUserHappyPath() {
        List<Order> orders = UtilUser.get().getOrders();
        when(ordersRepository.findAllByUser(ID)).thenReturn(orders);

        List<Order> actualAllOrdersByUser = orderService.getAllOrdersByUser(UtilUser.get());

        assertEquals(orders, actualAllOrdersByUser);
        assertTrue(actualAllOrdersByUser.isEmpty());
        verify(ordersRepository,times(1)).findAllByUser(ID);
    }

    @Test
    public void testGetAllOrdersByUserNegativePath() {

        when(ordersRepository.findAllByUser(anyInt())).thenThrow(new ResourceNotFoundException());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getAllOrdersByUser(UtilUser.get()));

        verify(ordersRepository).findAllByUser(ID);
    }
}
