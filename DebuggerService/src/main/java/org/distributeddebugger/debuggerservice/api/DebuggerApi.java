package org.distributeddebugger.debuggerservice.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.distributeddebugger.debuggerservice.dto.TransactionDetailsResponse;
import org.springframework.http.ResponseEntity;


public interface DebuggerApi {

    @Operation(
            summary = "Debug transaction flow",
            description = "Debug various transaction flows and returns details about the transaction using the correlation Id."
    )

    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "debug transactions",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TransactionDetailsResponse.class)
                    )
            )
    )
    public ResponseEntity<TransactionDetailsResponse> getTransactions(String correlationId);
}
