package com.ws.crm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Data
@ToString(exclude = {"orders", "users"})
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @Column(name = "address")
    @NotBlank(message = "Address should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String address;

    @OneToMany(mappedBy = "project")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> orders;

    @ManyToMany
    @JoinTable(name = "users_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<User> users;
}
