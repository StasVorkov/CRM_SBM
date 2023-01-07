package com.ws.crm.dto;

import com.ws.crm.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogDTO {

    @Id
    private Integer id;

    private LocalDateTime change;

    private Status newStatus;

    @NotNull(message = "Order should not be null")
    private OrderDTO order;

}
