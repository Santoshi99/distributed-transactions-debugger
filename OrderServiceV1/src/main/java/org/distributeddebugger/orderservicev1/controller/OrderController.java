package org.distributeddebugger.orderservicev1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.distributeddebugger.orderservicev1.api.OrderApi;
import org.distributeddebugger.orderservicev1.dto.CreateOrderResponse;
import org.distributeddebugger.orderservicev1.dto.OrderRequest;
import org.distributeddebugger.orderservicev1.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Operations for creating customer orders")
public class OrderController implements OrderApi {


    private final OrderServiceImpl orderService;

    @Autowired
   public OrderController(OrderServiceImpl orderService){
       this.orderService = orderService;
   }

   @Override
   @PostMapping
   public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody OrderRequest request){

       CreateOrderResponse orderResponse = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
   }

}
