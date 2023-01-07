package com.ws.crm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "items")
@ToString(exclude = "order")
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String itemName;

    @Column(name = "unit_measurement")
    @NotBlank(message = "Unit measurement should not be empty")
    private String unitMeasurement;

    @Column(name = "amount")
    @NotNull(message = "Amount should not be null")
    @Positive(message = "Amount should be positive")
    private Double amount;

    @Column(name = "requirement_delivery_date")
    @NotNull (message = "Requirement Delivery Date should not be null")
    @Future (message = "Requirement Delivery Date should be future")
    private LocalDate requirementDeliveryDate;

    @Column(name = "estimated_delivery_date")
    @Future (message = "Estimated Delivery Date should be future")
    private LocalDate estimatedDeliveryDate;

    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;

    @Column(name = "notes")
    @Size(max = 1000, message = "Notes should be not over 1000 characters")
    private String notes;

    @ManyToOne()
    @NotNull (message = "Order should not be null")
    @JoinColumn(name = "order_id",referencedColumnName = "order_id")
    private Order order;
}
