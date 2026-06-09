package org.distributeddebugger.orderservicev1;

import org.distributeddebugger.orderservicev1.entity.Order;
import org.distributeddebugger.orderservicev1.entity.OrderItem;
import org.distributeddebugger.orderservicev1.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void save_shouldPersistOrderWithItems() {
        Order order = Order.builder()
                .orderId("order-123")
                .customerId("customer-123")
                .amount(new BigDecimal("1499.99"))
                .correlationId("co-123")
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        OrderItem item = new OrderItem();
        item.setProductId("product-101");
        item.setQuantity(2);
        item.setPrice(new BigDecimal("749.995"));
        item.setOrder(order);

        order.setOrderItems(List.of(item));

        Order savedOrder = orderRepository.saveAndFlush(order);

        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getOrderItems()).hasSize(1);
        assertThat(savedOrder.getOrderItems().getFirst().getItemId()).isNotNull();
    }
}
