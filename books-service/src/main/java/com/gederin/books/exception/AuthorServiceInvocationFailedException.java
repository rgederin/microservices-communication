package com.gederin.books.exception;

public class AuthorServiceInvocationFailedException extends RuntimeException {
    public AuthorServiceInvocationFailedException(String message) {
        super(message);
    }
}
