package com.github.pankajyogi.spring.taxibooking.exception;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(String message) {
        super(message);
    }
}
