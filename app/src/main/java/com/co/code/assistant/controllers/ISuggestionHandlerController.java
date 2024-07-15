package com.co.code.assistant.controllers;

public interface ISuggestionHandlerController<P, B, R> {
    R handle(P params, B body);

    boolean isValidRequest(P params, B body);
}
