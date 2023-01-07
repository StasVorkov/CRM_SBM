package com.ws.crm.controllers;

import com.ws.crm.dto.*;
import com.ws.crm.models.Log;
import com.ws.crm.models.Order;
import com.ws.crm.models.Project;
import com.ws.crm.models.User;
import com.ws.crm.services.LogService;
import com.ws.crm.services.OrderService;
import com.ws.crm.services.ProjectService;

import com.ws.crm.services.implementations.CrmUserDetails;
import com.ws.crm.util.converter.DTOConvertable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
@Log4j
public class OrdersController {
    private final OrderService orderService;
    private final ProjectService projectService;
    private final LogService logService;
    private final DTOConvertable<OrderDTO, Order, Boolean> orderDTOConverter;
    private final DTOConvertable<ProjectDTO, Project, Boolean> projectDTOConverter;
    private final DTOConvertable<LogDTO, Log, Boolean> logDTOConverter;


    @GetMapping()
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrdersDeep();
        model.addAttribute("ordersDTO", orderDTOConverter.convertListToDTO(orders, false));
        return "/orders/orders";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable int id, Model model) {
        try{
            Order order = orderService.getOrderById(id);
            model.addAttribute("orderDTO", orderDTOConverter.convertToDTO(order, true));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return "/orders/single_order";
    }

    @GetMapping("/byUser")
    public String getAllOrdersByUser(Model model, @AuthenticationPrincipal CrmUserDetails crmUserDetails) {
        User authUser = crmUserDetails.getUser();
        List<Order> orders = orderService.getAllOrdersByUser(authUser);
        model.addAttribute("ordersDTO", orderDTOConverter.convertListToDTO(orders,false));
        return "/orders/orders";
    }

    @GetMapping("/new")
    public String getFormForCreatingOrder(Model model,
                                          @ModelAttribute("order") Order order,
                                          @AuthenticationPrincipal CrmUserDetails crmUserDetails) {
        addProjectsToModel(model, crmUserDetails);
        return "/orders/new_order";
    }

    private void addProjectsToModel(Model model, CrmUserDetails crmUserDetails) {
        User authUser = crmUserDetails.getUser();
        List<Project> projects = projectService.getAllProjectsByUser(authUser);
        List<ProjectDTO> projectsDTO = projectDTOConverter.convertListToDTO(projects, false);
        model.addAttribute("projectsDTO", projectsDTO);
    }

    @PostMapping("/new")
    public String createOrder(Model model,
                              @ModelAttribute("order") Order order,
                              @AuthenticationPrincipal CrmUserDetails crmUserDetails,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            addProjectsToModel(model, crmUserDetails);
            return "/orders/new_order";
        }
        try {
            orderService.save(order);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return "redirect:/orders/byUser";
    }

    @GetMapping("/{id}/edit")
    public String editOrderById(@PathVariable int id, Model model) {
        try {
            Order order = orderService.getOrderById(id);
            model.addAttribute("orderDTO", orderDTOConverter.convertToDTO(order, true));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return "/orders/edit_order";
    }

    @PatchMapping("/{id}")
    public String updateOrder(@ModelAttribute("orderDTO") @Valid OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/orders/edit_order";
        }
        orderService.update(orderDTOConverter.convertToEntity(orderDTO));
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/log")
    public String getOrderLog(@PathVariable int id, Model model) {
        List<Log> logs = logService.getLogsByOrderId(id);
        model.addAttribute("logsDTO", logDTOConverter.convertListToDTO(logs, false));
        return "/orders/logs";
    }

}
