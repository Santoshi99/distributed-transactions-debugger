package org.distributeddebugger.debuggerservice.service;

import org.distributeddebugger.debuggerservice.dto.TransactionDetailsResponse;
import org.distributeddebugger.debuggerservice.event.TransactionEventPayload;

public interface DebuggerService {
    void processOrderCreatedEvent(TransactionEventPayload transactionEventPayload);
    TransactionDetailsResponse getTransactions(String correlationId);
}
