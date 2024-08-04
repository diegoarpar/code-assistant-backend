package com.co.code.assistant.providers.geminis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminisAIRequestDto {
    public List<GeminisAIRequestContentDto> contents;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeminisAIRequestContentDto {
        public List<GeminisAIRequestPartsDto> parts;
        public String role;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeminisAIRequestPartsDto {
        public String text;
    }
}
