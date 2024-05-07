package com.example.projectmanagerkea.exceptions;

public class DatabaseOperationException extends Exception {
    public DatabaseOperationException(String message, Throwable err) {
        super(message, err);
    }
}
