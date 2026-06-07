package org.distributeddebugger.orderservicev1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(

        @NotBlank(message = "product Id should not be empty")
        String productId,

        @NotNull(message = "quantity should not be empty")
        @Min(value = 1, message = "quantity should be at least 1")
        int quantity
) {}
