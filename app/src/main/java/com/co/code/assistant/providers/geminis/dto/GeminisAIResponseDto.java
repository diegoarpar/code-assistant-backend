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
public class GeminisAIResponseDto {

    public List<GeminisAIResponseCandidateDto> candidates;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeminisAIResponseCandidateDto {
        public GeminisAIResponseContentDto content;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeminisAIResponseContentDto {
        public List<GeminisAIResponsePartsContentDto> parts;
        public String role;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeminisAIResponsePartsContentDto {
        public String text;
    }
}
