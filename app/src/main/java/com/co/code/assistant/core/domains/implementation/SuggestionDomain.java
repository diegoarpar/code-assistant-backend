package com.co.code.assistant.core.domains.implementation;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import lombok.Builder;

@Builder
public class SuggestionDomain implements ISuggestionDomain {

    private String exampleId;

    @Override
    public String getExampleId() {
        return exampleId;
    }

    @Override
    public void setExampleId(String exampleId) {
        this.exampleId = exampleId;
    }

    @Override
    public ISuggestionDomain getEmptyObject() {
        return SuggestionDomain.builder().build();
    }
}
