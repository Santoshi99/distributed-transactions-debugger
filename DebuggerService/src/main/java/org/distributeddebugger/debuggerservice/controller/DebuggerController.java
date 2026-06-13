package org.distributeddebugger.debuggerservice.controller;

import org.distributeddebugger.debuggerservice.dto.TransactionDetailsResponse;
import org.distributeddebugger.debuggerservice.service.DebuggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class DebuggerController {

    private final DebuggerService debuggerService;

    @Autowired
    public DebuggerController(DebuggerService debuggerService){
        this.debuggerService = debuggerService;
    }

    @GetMapping("/{correlationId}")
    public ResponseEntity<TransactionDetailsResponse> getTransactions(@PathVariable String correlationId){
        TransactionDetailsResponse response = debuggerService.getTransactions(correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
