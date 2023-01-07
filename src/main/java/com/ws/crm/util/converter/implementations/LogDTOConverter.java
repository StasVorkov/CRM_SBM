package com.ws.crm.util.converter.implementations;

import com.ws.crm.dto.LogDTO;
import com.ws.crm.dto.OrderDTO;
import com.ws.crm.models.Log;
import com.ws.crm.models.Order;
import com.ws.crm.util.converter.DTOConvertable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogDTOConverter implements DTOConvertable<LogDTO, Log, Boolean> {

    private final ModelMapper modelMapper;
    private final DTOConvertable<OrderDTO, Order, Boolean> orderDTOConverter;

    @Autowired
    public LogDTOConverter(ModelMapper modelMapper, DTOConvertable<OrderDTO, Order, Boolean> orderDTOConverter) {
        this.modelMapper = modelMapper;
        this.orderDTOConverter = orderDTOConverter;
    }

    @Override
    public List<LogDTO> convertListToDTO(List<Log> allItems, Boolean deepMode) {
        return allItems.stream().map(log -> convertToDTO(log,deepMode)).toList();
    }
    @Override
    public LogDTO convertToDTO(Log log, Boolean deepMode) {
        return deepMode ? deepConvert(log) : shallowConvert(log);
    }

    private LogDTO deepConvert(Log log) {
        LogDTO logDTO = shallowConvert(log);
        logDTO.setOrder(orderDTOConverter.convertToDTO(log.getOrder(),true));
        return logDTO;
    }

    private LogDTO shallowConvert(Log log) {
        LogDTO logDTO = new LogDTO();
        logDTO.setId(log.getId());
        logDTO.setChange(log.getChange());
        logDTO.setNewStatus(log.getNewStatus());
        return logDTO;
    }

    @Override
    public Log convertToEntity(LogDTO logDTO) {
        return modelMapper.map(logDTO, Log.class);
    }

}


