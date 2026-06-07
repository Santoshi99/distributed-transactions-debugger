package org.distributeddebugger.orderservicev1.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @OneToMany(mappedBy = "id")
    private List<OrderItem> orderItems;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    private String customerId;

    private String correlationId;

    private String Status;
}
