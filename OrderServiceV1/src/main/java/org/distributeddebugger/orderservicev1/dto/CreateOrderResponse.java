package org.distributeddebugger.orderservicev1.dto;

public record CreateOrderResponse(
        String message,
        String code,
        Long orderId,
        String correlationId,
        String status
) {
}