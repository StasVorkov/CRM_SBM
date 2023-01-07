package com.ws.crm.models;

import com.ws.crm.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logs")
public class Log {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "change")
    private LocalDateTime change;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status newStatus;

    @ManyToOne()
    @NotNull(message = "Order should not be null")
    @JoinColumn(name = "order_id",referencedColumnName = "order_id")
    private Order order;

}
