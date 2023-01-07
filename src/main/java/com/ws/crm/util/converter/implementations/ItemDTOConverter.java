package com.ws.crm.util.converter.implementations;

import com.ws.crm.dto.ItemDTO;
import com.ws.crm.dto.OrderDTO;
import com.ws.crm.models.Item;
import com.ws.crm.models.Order;
import com.ws.crm.util.converter.DTOConvertable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemDTOConverter implements DTOConvertable<ItemDTO, Item,Boolean> {

    private final ModelMapper modelMapper;
    private final DTOConvertable<OrderDTO,Order,Boolean> orderDTOConverter;

    @Autowired
    public ItemDTOConverter(ModelMapper modelMapper, DTOConvertable<OrderDTO, Order, Boolean> orderDTOConverter) {
        this.modelMapper = modelMapper;
        this.orderDTOConverter = orderDTOConverter;
    }

    @Override
    public List<ItemDTO> convertListToDTO(List<Item> allItems, Boolean deepMode) {
        return allItems.stream().map(item -> convertToDTO(item, deepMode)).toList();
    }

    @Override
    public ItemDTO convertToDTO(Item item, Boolean deepMode) {
        return deepMode ? deepConvert(item) : shallowConvert(item);
    }

    private ItemDTO deepConvert(Item item) {
        ItemDTO itemDTO = shallowConvert(item);
        itemDTO.setOrder(orderDTOConverter.convertToDTO(item.getOrder(),false));
        return itemDTO;
    }

    private ItemDTO shallowConvert(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setItemName(item.getItemName());
        itemDTO.setUnitMeasurement(item.getUnitMeasurement());
        itemDTO.setAmount(item.getAmount());
        itemDTO.setRequirementDeliveryDate(item.getRequirementDeliveryDate());
        itemDTO.setEstimatedDeliveryDate(item.getEstimatedDeliveryDate());
        itemDTO.setActualDeliveryDate(item.getActualDeliveryDate());
        itemDTO.setNotes(item.getNotes());
        return itemDTO;
    }

    @Override
    public Item convertToEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Item.class);
    }
}



