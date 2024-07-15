package com.co.code.assistant.exceptions.api;

public class ApiBadRequestException extends ApiApplicationException {
    public ApiBadRequestException(String errorCode, String message) {
        super(errorCode, 400, message);
    }

    public ApiBadRequestException(String message) {
        this("bad_request", message);
    }
}
