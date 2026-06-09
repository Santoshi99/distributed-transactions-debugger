package org.distributeddebugger.orderservicev1;

import org.distributeddebugger.orderservicev1.controller.OrderController;
import org.distributeddebugger.orderservicev1.dto.CreateOrderResponse;
import org.distributeddebugger.orderservicev1.exception.InvalidOrderAmountException;
import org.distributeddebugger.orderservicev1.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderServiceImpl orderService;

    @Test
    void createOrder_shouldReturnCreated_whenRequestIsValid() throws Exception {
        CreateOrderResponse response = new CreateOrderResponse(
                "Order successfully created",
                "ORDER_CREATED",
                "order-123",
                "co-123",
                "PENDING"
        );

        when(orderService.createOrder(any())).thenReturn(response);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validOrderRequestJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Order successfully created"))
                .andExpect(jsonPath("$.code").value("ORDER_CREATED"))
                .andExpect(jsonPath("$.orderId").value("order-123"))
                .andExpect(jsonPath("$.correlationId").value("co-123"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void createOrder_shouldReturnBadRequest_whenRequestValidationFails() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerId": "",
                                  "items": [],
                                  "amount": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void createOrder_shouldReturnBadRequest_whenServiceThrowsInvalidAmountException() throws Exception {
        when(orderService.createOrder(any()))
                .thenThrow(new InvalidOrderAmountException("Invalid order amount"));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validOrderRequestJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid order amount"))
                .andExpect(jsonPath("$.errorCode").value("INVALID_AMOUNT"))
                .andExpect(jsonPath("$.status").value(400));
    }

    private String validOrderRequestJson() {
        return """
                {
                  "customerId": "customer-123",
                  "items": [
                    {
                      "productId": "product-101",
                      "quantity": 2
                    }
                  ],
                  "amount": 1499.99
                }
                """;
    }
}
