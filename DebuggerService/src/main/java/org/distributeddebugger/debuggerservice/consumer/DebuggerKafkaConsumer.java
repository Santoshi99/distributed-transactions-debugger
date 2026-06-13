package org.distributeddebugger.debuggerservice.consumer;

import org.springframework.messaging.handler.annotation.Header;import org.distributeddebugger.debuggerservice.event.TransactionEventPayload;
import org.distributeddebugger.debuggerservice.service.DebuggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DebuggerKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(DebuggerKafkaConsumer.class);
    private final DebuggerService debuggerService;

    @Autowired
    public DebuggerKafkaConsumer(DebuggerService debuggerService){
        this.debuggerService = debuggerService;
    }

    @KafkaListener(topics = "${app.kafka.topics.transaction-events}",
    groupId = "${spring.kafka.consumer.group-id}")
    public void consumePayload(@Payload TransactionEventPayload eventPayload,
                               @Header(KafkaHeaders.RECEIVED_KEY) String key,
                               @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                               @Header(KafkaHeaders.OFFSET) long offset
                               ) {

        log.info("Consumed transaction event: correlationId={}, service={}, eventType={}, status={}",
                eventPayload.correlationId(),
                eventPayload.service(),
                eventPayload.eventType(),
                eventPayload.status());
        log.info("Key: {}, Partition: {}, Offset: {}", key, partition, offset);

        debuggerService.processOrderCreatedEvent(eventPayload);
    }

}
