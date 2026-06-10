package org.distributeddebugger.orderservicev1.event;

import java.time.LocalDateTime;

public record OrderCreatedEvent(
        String eventId,
        String correlationId,
        String orderId,
        String service,
        String eventType,
        String status,
        LocalDateTime timestamp
) {
}
