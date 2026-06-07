package org.distributeddebugger.orderservicev1.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        @NotBlank(message = "Customer ID is required")
        String customerId,

        @NotEmpty(message = "Order items are required")
        List<OrderItemRequest> items,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "1.00", message = "Amount must be at least 1.00")
        BigDecimal amount
) {
}
