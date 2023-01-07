package com.ws.crm.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {

    private Integer id;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Address should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String address;

    private List<OrderDTO> orders;

    private List<UserDTO> users;

}
