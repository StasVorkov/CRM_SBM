package com.ws.crm.models;

import com.ws.crm.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Data
@ToString(exclude = {"orders", "projects"})
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "user_id")
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Surname should not be empty")
    @Size(min = 2, max = 50, message = "Surname should be between 2 and 50 characters")
    private String surname;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email should not be empty")
    @Email
    private String email;

    @Column(name = "phone",unique = true)
    @Pattern(regexp = "^\\+\\d{11,12}$", message = "Phone should start with + and consist of 11 or 12 digits")
    @NotBlank(message = "Phone should not be empty")
    private String phone;

    @Column(name = "password")
    @Size(min = 8, message = "Password should consist at least 8 characters")
    @NotBlank(message = "Password should not be empty")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> orders;

    @ManyToMany
    @JoinTable(name = "users_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

}
