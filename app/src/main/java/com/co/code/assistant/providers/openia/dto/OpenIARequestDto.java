package com.co.code.assistant.providers.openia.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class OpenIARequestDto {
    public String model;
    public List<OpenIAMessage> messages;

    @Builder
    public static class OpenIAMessage {
        public String role;
        public String content;
    }
}
