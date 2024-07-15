package com.co.code.assistant.providers.items.dto;

import lombok.Builder;

@Builder
public class SuggestionDto implements ISuggestionDto {
    private String id;
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
