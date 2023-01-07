package com.ws.crm.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ItemDTO {

    private Integer id;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String itemName;

    @NotBlank(message = "Unit measurement should not be empty")
    private String unitMeasurement;

    @NotNull(message = "Amount should not be null")
    @Positive(message = "Amount should be positive")
    private Double amount;


    @NotNull (message = "Requirement Delivery Date should not be null")
    @Future (message = "Requirement Delivery Date should be future")
    private LocalDate requirementDeliveryDate;

    @Future (message = "Estimated Delivery Date should be future")
    private LocalDate estimatedDeliveryDate;

    private LocalDate actualDeliveryDate;

    @Size(max = 1000, message = "Notes should be not over 1000 characters")
    private String notes;

    @NotNull (message = "Order should not be null")
    private OrderDTO order;

}
