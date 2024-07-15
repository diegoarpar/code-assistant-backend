package com.co.code.assistant.core.usecases.suggestion.model;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import lombok.Builder;

@Builder
public class SuggestionUseCaseModel {
    private ISuggestionDomain exampleEntity;
    private ISuggestionDomain example2Entity;
    private ISuggestionDomain example3Entity;


}
