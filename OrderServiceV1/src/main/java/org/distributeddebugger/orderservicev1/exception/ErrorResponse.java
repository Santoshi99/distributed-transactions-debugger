package org.distributeddebugger.orderservicev1.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Standard error response")
public record ErrorResponse(
        @Schema(description = "Error details", example = "amount:Amount must be at least 1.00")
        String message,

        @Schema(description = "Machine-readable error code", example = "VALIDATION_FAILED")
        String errorCode,

        @Schema(description = "HTTP status code", example = "400")
        int status,

        @Schema(description = "Time when the error occurred", example = "2026-06-07T22:45:00")
        LocalDateTime timestamp
) {
}
