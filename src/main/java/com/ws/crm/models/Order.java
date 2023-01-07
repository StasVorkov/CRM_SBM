package com.ws.crm.models;

import com.ws.crm.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString(exclude = {"items","user","project","logs"})
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "requirement_delivery_date")
    @Future(message = "Requirement Delivery Date should be future")
    @NotNull(message = "Requirement Delivery Date should not be null")
    private LocalDate requirementDeliveryDate;

    @Column(name = "estimated_delivery_date")
    @Future
    private LocalDate estimatedDeliveryDate;

    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Log> logs;

    @ManyToOne()
    @NotNull(message = "Project should not be null")
    @JoinColumn(name = "project_id",referencedColumnName = "project_id")
    private Project project;

    @ManyToOne()
    @NotNull(message = "User should not be null")
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Item> items;
}
