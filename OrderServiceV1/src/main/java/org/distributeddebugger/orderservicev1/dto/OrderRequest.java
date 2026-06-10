package org.distributeddebugger.orderservicev1.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Request payload for creating an order")
public record OrderRequest(
        @NotBlank(message = "Customer ID is required")
        @Schema(description = "Unique customer identifier", example = "customer-123", requiredMode = Schema.RequiredMode.REQUIRED)
        String customerId,

        @NotEmpty(message = "Order items are required")
        @ArraySchema(
                schema = @Schema(implementation = OrderItemRequest.class),
                minItems = 1
        )
        List<@Valid OrderItemRequest> items,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "1.00", message = "Amount must be at least 1.00")
        @Schema(description = "Total order amount", example = "1499.99", minimum = "1.00", requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal amount
) {
}
