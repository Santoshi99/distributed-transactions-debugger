package org.distributeddebugger.orderservicev1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned after an order is created")
public record CreateOrderResponse(
        @Schema(description = "Human-readable result message", example = "Order successfully created")
        String message,

        @Schema(description = "Machine-readable result code", example = "ORDER_CREATED")
        String code,

        @Schema(description = "Generated order identifier", example = "6e92f2b1-dbb6-43a7-8e3c-80d77fb56de6")
        String orderId,

        @Schema(description = "Correlation identifier for tracing the order flow", example = "corel-f54c8770-c4c2-45ec-8fe9-86ec39557fe1")
        String correlationId,

        @Schema(description = "Current order status", example = "CREATED")
        String status
) {
}
