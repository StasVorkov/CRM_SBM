package com.ws.crm.dto;

import com.ws.crm.models.enums.Status;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@Data
@ToString(exclude = {"items","user","project","logs"})
public class OrderDTO {

    private Integer id;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    private LocalDate orderDate;

    @Future(message = "Requirement Delivery Date should be future")
    @NotNull(message = "Requirement Delivery Date should not be null")
    private LocalDate requirementDeliveryDate;

    @Future
    private LocalDate estimatedDeliveryDate;

    private LocalDate actualDeliveryDate;

    private Status status;

    private List<LogDTO> logs;

    @NotNull(message = "Project should not be null")
    private ProjectDTO project;

    @NotNull(message = "User should not be null")
    private UserDTO user;

    private List<ItemDTO> items;

}
