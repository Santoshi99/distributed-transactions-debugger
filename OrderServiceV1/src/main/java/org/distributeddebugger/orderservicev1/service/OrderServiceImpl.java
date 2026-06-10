package org.distributeddebugger.orderservicev1.service;

import jakarta.validation.Valid;
import org.distributeddebugger.orderservicev1.dto.CreateOrderResponse;
import org.distributeddebugger.orderservicev1.dto.OrderRequest;
import org.distributeddebugger.orderservicev1.entity.Order;
import org.distributeddebugger.orderservicev1.entity.OrderItem;
import org.distributeddebugger.orderservicev1.event.OrderCreatedEvent;
import org.distributeddebugger.orderservicev1.producer.OrderCreatedProducer;
import org.distributeddebugger.orderservicev1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final OrderCreatedProducer orderCreatedProducer;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderCreatedProducer orderCreatedProducer){
        this.orderRepository = orderRepository;
        this.orderCreatedProducer = orderCreatedProducer;
    }

    public CreateOrderResponse createOrder(@Valid OrderRequest request) {

        String orderId = UUID.randomUUID().toString();
        String correlationId = "co-" + UUID.randomUUID();
        List<OrderItem> itemsList = request.items().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.productId());
            orderItem.setQuantity(item.quantity());
            return orderItem;
        }).toList();

        Order order = Order.builder().createdAt(LocalDateTime.now())
                .orderItems(itemsList)
                .amount(request.amount())
                .customerId(request.customerId())
                .orderId(orderId)
                .correlationId(correlationId)
                .status("PENDING")
                .build();

        itemsList.forEach(orderItem -> orderItem.setOrder(order));
        orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                "EVT-" + UUID.randomUUID(),
                order.getCorrelationId(),
                order.getOrderId(),
                "ORDER_SERVICE",
                "ORDER_CREATED",
                "SUCCESS",
                LocalDateTime.now()
        );
        orderCreatedProducer.publishOrderCreatedEvent(orderCreatedEvent);

        return new CreateOrderResponse("Order successfully created", "ORDER_CREATED", order.getOrderId(), order.getCorrelationId(), order.getStatus());
    }
}
