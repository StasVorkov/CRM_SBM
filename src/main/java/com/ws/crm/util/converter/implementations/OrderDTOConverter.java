package com.ws.crm.util.converter.implementations;

import com.ws.crm.dto.*;
import com.ws.crm.models.*;
import com.ws.crm.util.converter.DTOConvertable;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDTOConverter implements DTOConvertable<OrderDTO, Order, Boolean> {

    private final ModelMapper modelMapper;
    private final DTOConvertable<ProjectDTO,Project, Boolean> projectDTOConverter;
    private final DTOConvertable<UserDTO, User, Boolean> userDTOConverter;
    private final DTOConvertable<ItemDTO, Item, Boolean> itemDTOConverter;
    private final DTOConvertable<LogDTO, Log, Boolean> logDTOConverter;

    @Autowired
    public OrderDTOConverter(ModelMapper modelMapper,
                             @Lazy DTOConvertable<ProjectDTO,Project, Boolean> projectDTOConverter,
                             @Lazy DTOConvertable<UserDTO, User, Boolean> userDTOConverter,
                             @Lazy DTOConvertable<ItemDTO, Item, Boolean> itemDTOConverter,
                             @Lazy DTOConvertable<LogDTO, Log, Boolean> logDTOConverter) {
        this.modelMapper = modelMapper;
        this.projectDTOConverter = projectDTOConverter;
        this.userDTOConverter = userDTOConverter;
        this.itemDTOConverter = itemDTOConverter;
        this.logDTOConverter = logDTOConverter;
    }

    @Override
    public List<OrderDTO> convertListToDTO(List<Order> orders, Boolean deepMode) {
        return orders.stream().map(order -> convertToDTO(order,deepMode)).toList();
    }

    @Override
    public Order convertToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }


    @Override
    public OrderDTO convertToDTO(Order order, Boolean deepMode) {
        return deepMode ? deepConvert(order) : shallowConvert(order);
    }

    private OrderDTO deepConvert(Order order) {
        OrderDTO orderDTO = shallowConvert(order);
        orderDTO.setProject(projectDTOConverter.convertToDTO(order.getProject(),false));
        orderDTO.setUser(userDTOConverter.convertToDTO(order.getUser(),false));
        orderDTO.setItems(itemDTOConverter.convertListToDTO(order.getItems(),false));
        orderDTO.setLogs(logDTOConverter.convertListToDTO(order.getLogs(),false));
        return orderDTO;
    }

    private OrderDTO shallowConvert(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setName(order.getName());
        orderDTO.setRequirementDeliveryDate(order.getRequirementDeliveryDate());
        orderDTO.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
        orderDTO.setActualDeliveryDate(order.getActualDeliveryDate());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

}
