package org.distributeddebugger.debuggerservice.repository;

import org.distributeddebugger.debuggerservice.dto.TransactionDetailsResponse;
import org.distributeddebugger.debuggerservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DebuggerRepository extends JpaRepository<Transaction, Long> {

    public Transaction findByEventId(String eventId);

    @Query("""
            SELECT new org.distributeddebugger.debuggerservice.dto.TransactionDetailsResponse(
                t.eventId,
                t.correlationId,
                t.serviceName,
                t.entityType,
                t.status
            )
            FROM Transaction t
            WHERE t.correlationId = :correlationId
            """)
    public TransactionDetailsResponse findByCorrelationId(String correlationId);
}
