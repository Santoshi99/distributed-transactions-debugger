package org.distributeddebugger.debuggerservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String eventId;

    private String correlationId;
    private String serviceName;
    private String entityId;
    private String entityType;
    private String status;
    private LocalDateTime eventCreatedAt;
    private LocalDateTime receivedAt;
}
