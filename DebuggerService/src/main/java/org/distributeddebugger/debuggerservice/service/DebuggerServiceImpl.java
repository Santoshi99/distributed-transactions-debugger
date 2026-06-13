package org.distributeddebugger.debuggerservice.service;

import org.distributeddebugger.debuggerservice.dto.TransactionDetailsResponse;
import org.distributeddebugger.debuggerservice.entity.Transaction;
import org.distributeddebugger.debuggerservice.event.TransactionEventPayload;
import org.distributeddebugger.debuggerservice.exception.DuplicateEventException;
import org.distributeddebugger.debuggerservice.exception.TransactionNotFoundException;
import org.distributeddebugger.debuggerservice.repository.DebuggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class DebuggerServiceImpl implements DebuggerService{

    private static final Logger log = LoggerFactory.getLogger(DebuggerServiceImpl.class);
    private final DebuggerRepository debuggerRepository;

    @Autowired
    public DebuggerServiceImpl(DebuggerRepository debuggerRepository){
        this.debuggerRepository = debuggerRepository;
    }

    @Override
    public void processOrderCreatedEvent(TransactionEventPayload transactionEventPayload) {
        Transaction transactionDetails = debuggerRepository.findByEventId(transactionEventPayload.eventId());
        if (transactionDetails != null) {
            log.error("Event ID already exists, eventID: {}, serviceName: {}, status: {}",
                    transactionDetails.getEventId(),
                    transactionDetails.getServiceName(),
                    transactionDetails.getStatus());

            throw new DuplicateEventException("Event ID already exists");
        }

        Transaction transaction = new Transaction();
        transaction.setEventId(transactionEventPayload.eventId());
        transaction.setCorrelationId(transactionEventPayload.correlationId());
        transaction.setStatus(transactionEventPayload.status());
        transaction.setEntityId(transactionEventPayload.eventId());
        transaction.setEventCreatedAt(transactionEventPayload.timestamp());
        transaction.setServiceName(transactionEventPayload.service());
        transaction.setEntityType(transactionEventPayload.eventType());
        transaction.setReceivedAt(LocalDateTime.now());

        debuggerRepository.save(transaction);
    }

    @Override
    public TransactionDetailsResponse getTransactions(String correlationId){
        TransactionDetailsResponse response =  debuggerRepository.findByCorrelationId(correlationId);
        if(response == null){
            throw new TransactionNotFoundException("Transaction doesn't exist with correlationId");
        }
        return response;
    }
}
