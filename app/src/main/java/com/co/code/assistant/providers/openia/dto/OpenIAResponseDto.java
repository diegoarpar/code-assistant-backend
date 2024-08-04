package com.co.code.assistant.providers.openia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenIAResponseDto {
    public String finishReason;
    public List<OpenIAChoice> choices;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OpenIAChoice {
        public OpenIAMessage message;
        public String finishReason;
        public OpenIAUsage usage;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OpenIAMessage {
        public String role;
        public String content;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OpenIAUsage {
        public String promptTokens;
        public String completionTokens;
        public String totalTokens;
    }
}
