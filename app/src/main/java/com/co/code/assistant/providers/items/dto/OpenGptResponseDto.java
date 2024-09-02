package com.co.code.assistant.providers.items.dto;


public class OpenGptResponseDto implements ISuggestionDto {
    public String id;
    public String content;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
