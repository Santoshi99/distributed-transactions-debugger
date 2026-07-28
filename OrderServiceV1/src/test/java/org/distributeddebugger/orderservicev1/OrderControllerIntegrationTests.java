package org.distributeddebugger.orderservicev1;

import org.distributeddebugger.orderservicev1.entity.Order;
import org.distributeddebugger.orderservicev1.entity.OrderItem;
import org.distributeddebugger.orderservicev1.producer.OrderCreatedProducer;
import org.distributeddebugger.orderservicev1.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @MockitoBean
    private OrderCreatedProducer orderCreatedProducer;

    @BeforeEach
    void cleanDatabase() {
        orderRepository.deleteAll();
    }

    @Test
    void createOrder_shouldReturnCreatedAndPersistOrder_whenRequestIsValid() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerId": "customer-123",
                                  "items": [
                                    {
                                      "productId": "product-101",
                                      "quantity": 2
                                    },
                                    {
                                      "productId": "product-202",
                                      "quantity": 1
                                    }
                                  ],
                                  "amount": 1499.99
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Order successfully created"))
                .andExpect(jsonPath("$.code").value("ORDER_CREATED"))
                .andExpect(jsonPath("$.orderId").isNotEmpty())
                .andExpect(jsonPath("$.correlationId").isNotEmpty())
                .andExpect(jsonPath("$.status").value("PENDING"));

        List<Order> persistedOrders = orderRepository.findAll();
        assertThat(persistedOrders).hasSize(1);

        Order persistedOrder = persistedOrders.getFirst();
        assertThat(persistedOrder.getId()).isNotNull();
        assertThat(persistedOrder.getOrderId()).isNotBlank();
        assertThat(persistedOrder.getCorrelationId()).startsWith("co-");
        assertThat(persistedOrder.getCustomerId()).isEqualTo("customer-123");
        assertThat(persistedOrder.getAmount()).isEqualByComparingTo("1499.99");
        assertThat(persistedOrder.getStatus()).isEqualTo("PENDING");
        assertThat(persistedOrder.getCreatedAt()).isNotNull();
        assertThat(persistedOrder.getOrderItems())
                .extracting(OrderItem::getProductId)
                .containsExactlyInAnyOrder("product-101", "product-202");
        assertThat(persistedOrder.getOrderItems())
                .extracting(OrderItem::getQuantity)
                .containsExactlyInAnyOrder(2, 1);
    }

    @Test
    void createOrder_shouldReturnBadRequestAndNotPersistOrder_whenCustomerIdIsBlank() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerId": "",
                                  "items": [
                                    {
                                      "productId": "product-101",
                                      "quantity": 2
                                    }
                                  ],
                                  "amount": 1499.99
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("customerId")));

        assertThat(orderRepository.findAll()).isEmpty();
    }

    @Test
    void createOrder_shouldReturnBadRequestAndNotPersistOrder_whenItemsAreEmpty() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerId": "customer-123",
                                  "items": [],
                                  "amount": 1499.99
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("items")));

        assertThat(orderRepository.findAll()).isEmpty();
    }

    @Test
    void createOrder_shouldReturnBadRequestAndNotPersistOrder_whenQuantityIsLessThanOne() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerId": "customer-123",
                                  "items": [
                                    {
                                      "productId": "product-101",
                                      "quantity": 0
                                    }
                                  ],
                                  "amount": 1499.99
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("quantity")));

        assertThat(orderRepository.findAll()).isEmpty();
    }

    @Test
    void createOrder_shouldReturnBadRequestAndNotPersistOrder_whenAmountIsLessThanMinimum() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerId": "customer-123",
                                  "items": [
                                    {
                                      "productId": "product-101",
                                      "quantity": 2
                                    }
                                  ],
                                  "amount": 0.99
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("amount")));

        assertThat(orderRepository.findAll()).isEmpty();
    }
}
