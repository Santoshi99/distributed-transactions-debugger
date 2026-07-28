package org.distributeddebugger.debuggerservice.dto;

public record TransactionDetailsResponse(String eventId, String correlationId, String serviceName, String entityType, String status) {
}
