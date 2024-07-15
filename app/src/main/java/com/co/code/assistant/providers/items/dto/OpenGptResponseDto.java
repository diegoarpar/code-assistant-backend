package com.co.code.assistant.providers.items.dto;


public class OpenGptResponseDto implements ISuggestionDto {
    public String id;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
