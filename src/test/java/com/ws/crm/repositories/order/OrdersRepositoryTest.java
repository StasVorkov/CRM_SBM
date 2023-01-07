package com.ws.crm.repositories.order;

import com.ws.crm.models.Order;
import com.ws.crm.repositories.OrdersRepository;
import com.ws.crm.repositories.ProjectsRepository;
import com.ws.crm.repositories.UsersRepository;
import com.ws.crm.util.orders.UtilOrder;
import com.ws.crm.util.projects.UtilProject;
import com.ws.crm.util.users.UtilUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProjectsRepository projectsRepository;
    @BeforeEach
    public void saveUserAndProject() {
        usersRepository.save(UtilUser.get());
        projectsRepository.save(UtilProject.get());
    }
    @Test
    public void findOrderByIdCustom() {

        ordersRepository.save(UtilOrder.get());

        Optional<Order> orderFromRepository = ordersRepository.findOrderByIdCustom(UtilOrder.get().getId());

        orderFromRepository.ifPresent(project -> assertEquals(UtilOrder.get(), project));
    }

    @Test
    public void findAllOrdersByUserId() {

        for (int i = 1; i < 5; i++) {
            ordersRepository.save(UtilOrder.getNew(i));
        }
        List<Order> orders = ordersRepository.findAllByUser(UtilUser.get().getId());

        assertEquals(4,orders.size());
    }
}