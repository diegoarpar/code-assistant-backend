package com.co.code.assistant.providers.copilot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CopilotAIResponseDto {

    public List<CopilotAIResponseChoiceDto> choices;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CopilotAIResponseChoiceDto {
        public CopilotAIResponseMessageDto message;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CopilotAIResponseMessageDto {
        public String content;
        public String role;
    }

}
