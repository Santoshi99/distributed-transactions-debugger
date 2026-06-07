package org.distributeddebugger.orderservicev1.exception;

public class InvalidOrderAmountException extends RuntimeException {
    public InvalidOrderAmountException(String message) {
        super(message);
    }
}
