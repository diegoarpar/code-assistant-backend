package com.co.code.assistant.exceptions.api;

public class ApiNotFoundException extends ApiApplicationException {
    public ApiNotFoundException(String message) {
        super("not_found", 404, message);
    }
}
