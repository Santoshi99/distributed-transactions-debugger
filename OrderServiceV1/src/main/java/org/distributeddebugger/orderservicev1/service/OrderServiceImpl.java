package org.distributeddebugger.orderservicev1.service;

import jakarta.validation.Valid;
import org.distributeddebugger.orderservicev1.dto.CreateOrderResponse;
import org.distributeddebugger.orderservicev1.dto.OrderRequest;
import org.distributeddebugger.orderservicev1.entity.Order;
import org.distributeddebugger.orderservicev1.entity.OrderItem;
import org.distributeddebugger.orderservicev1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(@Valid OrderRequest request) {

        Long orderId = Long.parseLong(UUID.randomUUID().toString());
        String correlationId = "corel-" + UUID.randomUUID();
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
                .build();

        orderRepository.save(order);
        return new CreateOrderResponse("Order successfully created", "ORDER_CREATED", order.getOrderId(), order.getCorrelationId(), order.getStatus());
    }
}
