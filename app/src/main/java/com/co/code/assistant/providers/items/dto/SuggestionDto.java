package com.co.code.assistant.providers.items.dto;

import lombok.Builder;

@Builder
public class SuggestionDto implements ISuggestionDto {
    private String id;
    private String content;

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return this.content;
    }
}
