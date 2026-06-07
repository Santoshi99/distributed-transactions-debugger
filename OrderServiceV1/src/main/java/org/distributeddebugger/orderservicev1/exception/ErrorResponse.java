package org.distributeddebugger.orderservicev1.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String message, String errorCode, int status, LocalDateTime timestamp) {
}
