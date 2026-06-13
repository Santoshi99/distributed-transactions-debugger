package org.distributeddebugger.debuggerservice.event;

import java.time.LocalDateTime;

public record TransactionEventPayload(
        String eventId,
        String correlationId,
        String orderId,
        String service,
        String eventType,
        String status,
        LocalDateTime timestamp
) {
}
