package ru.pomau.security.exception;

public class CantCreateException extends Exception {
    public CantCreateException(String message) {
        super(message);
    }
}