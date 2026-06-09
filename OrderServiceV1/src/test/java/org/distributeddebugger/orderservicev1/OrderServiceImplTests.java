package org.distributeddebugger.orderservicev1;

import org.distributeddebugger.orderservicev1.dto.CreateOrderResponse;
import org.distributeddebugger.orderservicev1.dto.OrderItemRequest;
import org.distributeddebugger.orderservicev1.dto.OrderRequest;
import org.distributeddebugger.orderservicev1.entity.Order;
import org.distributeddebugger.orderservicev1.repository.OrderRepository;
import org.distributeddebugger.orderservicev1.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_shouldSaveOrderAndReturnCreatedResponse() {
        OrderRequest request = new OrderRequest(
                "customer-123",
                List.of(new OrderItemRequest("product-101", 2)),
                new BigDecimal("1499.99")
        );

        when(orderRepository.save(org.mockito.ArgumentMatchers.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderResponse response = orderService.createOrder(request);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());

        Order savedOrder = orderCaptor.getValue();
        assertThat(savedOrder.getCustomerId()).isEqualTo("customer-123");
        assertThat(savedOrder.getAmount()).isEqualByComparingTo("1499.99");
        assertThat(savedOrder.getStatus()).isEqualTo("PENDING");
        assertThat(savedOrder.getOrderId()).isNotBlank();
        assertThat(savedOrder.getCorrelationId()).startsWith("co-");
        assertThat(savedOrder.getOrderItems()).hasSize(1);
        assertThat(savedOrder.getOrderItems().getFirst().getProductId()).isEqualTo("product-101");
        assertThat(savedOrder.getOrderItems().getFirst().getQuantity()).isEqualTo(2);
        assertThat(savedOrder.getOrderItems().getFirst().getOrder()).isSameAs(savedOrder);

        assertThat(response.message()).isEqualTo("Order successfully created");
        assertThat(response.code()).isEqualTo("ORDER_CREATED");
        assertThat(response.orderId()).isEqualTo(savedOrder.getOrderId());
        assertThat(response.correlationId()).isEqualTo(savedOrder.getCorrelationId());
        assertThat(response.status()).isEqualTo("PENDING");
    }
}
