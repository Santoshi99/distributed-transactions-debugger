package org.distributeddebugger.orderservicev1.producer;

import lombok.RequiredArgsConstructor;
import org.distributeddebugger.orderservicev1.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCreatedProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publishOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent){
        kafkaTemplate.send("transaction-events", orderCreatedEvent.correlationId(), orderCreatedEvent);
    }
}
