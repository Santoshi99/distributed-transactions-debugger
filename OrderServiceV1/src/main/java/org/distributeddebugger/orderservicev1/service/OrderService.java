package org.distributeddebugger.orderservicev1.service;

import org.distributeddebugger.orderservicev1.dto.CreateOrderResponse;
import org.distributeddebugger.orderservicev1.dto.OrderItemRequest;

public interface OrderService {

     CreateOrderResponse createOrder(OrderItemRequest orderItemRequest);
}
