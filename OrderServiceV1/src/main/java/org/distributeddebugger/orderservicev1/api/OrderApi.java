package org.distributeddebugger.orderservicev1.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.distributeddebugger.orderservicev1.dto.CreateOrderResponse;
import org.distributeddebugger.orderservicev1.dto.OrderRequest;
import org.distributeddebugger.orderservicev1.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderApi {

    @Operation(
            summary = "Create an order",
            description = "Creates a new order for a customer and returns the generated order and correlation identifiers."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Order created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateOrderResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "message": "Order successfully created",
                                      "code": "ORDER_CREATED",
                                      "orderId": "6e92f2b1-dbb6-43a7-8e3c-80d77fb56de6",
                                      "correlationId": "corel-f54c8770-c4c2-45ec-8fe9-86ec39557fe1",
                                      "status": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid order request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "message": "amount:Amount must be at least 1.00",
                                      "errorCode": "VALIDATION_FAILED",
                                      "status": 400,
                                      "timestamp": "2026-06-07T22:45:00"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody OrderRequest request);
}
