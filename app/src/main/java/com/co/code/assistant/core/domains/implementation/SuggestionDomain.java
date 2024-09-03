package com.co.code.assistant.core.domains.implementation;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import lombok.Builder;

@Builder
public class SuggestionDomain implements ISuggestionDomain {

    private String id;
    private String name;


    @Override
    public String geId() {
        return id;
    }

    @Override
    public String getContent() {
        return name;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setContent(String name) {
        this.name = name;
    }

    @Override
    public ISuggestionDomain getEmptyObject() {
        return SuggestionDomain.builder().build();
    }
}
