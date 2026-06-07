package org.distributeddebugger.orderservicev1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Single product line item in an order")
public record OrderItemRequest(

        @NotBlank(message = "product Id should not be empty")
        @Schema(description = "Unique product identifier", example = "product-101", requiredMode = Schema.RequiredMode.REQUIRED)
        String productId,

        @NotNull(message = "quantity should not be empty")
        @Min(value = 1, message = "quantity should be at least 1")
        @Schema(description = "Quantity ordered for the product", example = "2", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        int quantity
) {}
