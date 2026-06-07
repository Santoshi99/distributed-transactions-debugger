package org.distributeddebugger.orderservicev1.controller;

import jakarta.validation.Valid;
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
public class OrderController {


    private final OrderServiceImpl orderService;

    @Autowired
   public OrderController(OrderServiceImpl orderService){
       this.orderService = orderService;
   }

   @PostMapping()
   public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody OrderRequest request){

       CreateOrderResponse orderResponse = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
   }

}
