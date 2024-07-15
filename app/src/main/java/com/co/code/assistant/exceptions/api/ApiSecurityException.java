package com.co.code.assistant.exceptions.api;

public class ApiSecurityException extends ApiApplicationException {
    public ApiSecurityException(String message) {
        super("forbidden", 403, message);
    }
}
