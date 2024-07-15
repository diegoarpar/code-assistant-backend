package com.co.code.assistant.core.usecases.suggestion.handlers;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.domains.implementation.SuggestionDomain;
import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SuggestionUseCaseHandler {

    @Inject
    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, String>> exampleClientProvider;

    public Observable<ISuggestionDomain> get() {
        ISuggestionDomain exampleEntity = SuggestionDomain.builder().exampleId("exampleId").build();
        Map<String, String> params = new ConcurrentHashMap<>();
        params.put("itemId", "MLM680915196");
        return exampleClientProvider.getInformation(params)
                .map(exampleDto -> {
                    exampleEntity.setExampleId(exampleDto.getId());
                    return exampleEntity;
                });
    }

}
