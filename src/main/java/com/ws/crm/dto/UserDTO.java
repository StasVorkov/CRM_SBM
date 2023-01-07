package com.ws.crm.dto;

import com.ws.crm.models.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude = {"orders", "projects"})
public class UserDTO {

    private Integer id;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Surname should not be empty")
    @Size(min = 2, max = 50, message = "Surname should be between 2 and 50 characters")
    private String surname;

    @NotBlank(message = "Email should not be empty")
    @Email
    private String email;

    @Pattern(regexp = "^\\+\\d{11,12}$", message = "Phone should start with + and consist of 11 or 12 digits")
    @NotBlank(message = "Phone should not be empty")
    private String phone;

    private Role role;

    private List<OrderDTO> orders;

    private List<ProjectDTO> projects;
}
