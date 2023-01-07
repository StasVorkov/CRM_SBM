package com.ws.crm.controllers;

import com.ws.crm.dto.ItemDTO;
import com.ws.crm.dto.OrderDTO;
import com.ws.crm.models.Item;
import com.ws.crm.models.Order;
import com.ws.crm.services.ItemService;
import com.ws.crm.services.OrderService;
import com.ws.crm.util.converter.DTOConvertable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
@Log4j
public class ItemsController {
    private final ItemService itemService;
    private final OrderService orderService;
    private final DTOConvertable<ItemDTO, Item,Boolean> itemDTOConverter;
    private final DTOConvertable<OrderDTO, Order, Boolean> orderDTOConverter;

    @GetMapping()
    public String getAllItems(Model model) {
        List<Item> items = itemService.findAllItems();
        List<ItemDTO> itemsDTO = itemDTOConverter.convertListToDTO(items,true);
        model.addAttribute("itemsDTO", itemsDTO);
        return "/items/items";
    }

    @GetMapping("/{id}")
    public String getItemById(@PathVariable int id, Model model) {
        try {
            Item item = itemService.getItemById(id);
            model.addAttribute("itemDTO", itemDTOConverter.convertToDTO(item, true));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return "/items/single_item";
    }

    @GetMapping("/new")
    public String getFormForCreatingItem(@ModelAttribute("item") Item item, Model model) {
        addAllOrdersToModel(model);
        return "/items/new_item";
    }

    private void addAllOrdersToModel(Model model) {
        List<Order> orders = orderService.getAllOrdersDeep();
        model.addAttribute("allOrders", orderDTOConverter.convertListToDTO(orders,false));
    }

    @PostMapping("/new")
    public String createItem(Model model, @ModelAttribute("item") @Valid Item item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            addAllOrdersToModel(model);
            return "/items/new_item";
        }
        itemService.saveItem(item);
        return "redirect:/items";
    }

    @GetMapping("/newByOrder/{orderId}")
    public String getFormForCreatingItemFromOrder(@PathVariable int orderId, @ModelAttribute("itemDTO") ItemDTO itemDTO) {
        Order order = orderService.getOrderById(orderId);
        itemDTO.setOrder(orderDTOConverter.convertToDTO(order,true));
        return "/items/new_item_from_order";
    }

    @PostMapping("/newByOrder/{orderId}")
    public String createItemFromOrder(@PathVariable int orderId, @ModelAttribute("itemDTO") @Valid ItemDTO itemDTO,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/items/new_item_from_order";
        }
        itemService.saveItem(itemDTOConverter.convertToEntity(itemDTO));
        return "redirect:../../orders/" + orderId;
    }

    @GetMapping("/{id}/edit")
    public String editItemById(@PathVariable int id, Model model) {
        try {
            Item item = itemService.getItemById(id);
            model.addAttribute("itemDTO", itemDTOConverter.convertToDTO(item, true));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return "/items/edit_item";
    }

    @PatchMapping("/{id}")
    public String updateItem(@ModelAttribute("itemDTO") @Valid ItemDTO itemDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/items/edit_item";
        }
        itemService.saveItem(itemDTOConverter.convertToEntity(itemDTO));
        return "redirect:/items";
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
        return "redirect:/items";
    }
}
