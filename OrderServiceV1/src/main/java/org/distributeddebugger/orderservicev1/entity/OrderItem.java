package org.distributeddebugger.orderservicev1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    private String productId;

    private int quantity;

    private BigDecimal price;
}
