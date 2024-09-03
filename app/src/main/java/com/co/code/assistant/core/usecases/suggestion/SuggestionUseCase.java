package com.co.code.assistant.core.usecases.suggestion;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.core.usecases.suggestion.handlers.SuggestionUseCaseHandler;
import com.co.code.assistant.core.usecases.suggestion.model.SuggestionUseCaseModel;
import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SuggestionUseCase implements SuggestionSafeUseCase<List<ISuggestionDomain>, Map<String, List<String>>> {



    @Inject
    private SuggestionUseCaseHandler suggestionUseCaseHandler;

    public SuggestionUseCaseModel getModel(ISuggestionDomain example2Dto, ISuggestionDomain example3Dto) {
        return SuggestionUseCaseModel.builder().example2Entity(example2Dto).example3Entity(example3Dto).build();
    }


    @Override
    public Observable<List<ISuggestionDomain>> fallback(Throwable t) {
        return Observable.just(getFallbackObject());
    }

    @Override
    public Observable<List<ISuggestionDomain>> run(Map<String, List<String>> params) {
        List<ISuggestionDomain> domains = new ArrayList<>();
        return Observable.fromCallable(() -> {
                    return suggestionUseCaseHandler.get(params)
                            .map(domainList -> {
                                return domainList;
                            });
                }).flatMap(iExampleEntity -> iExampleEntity)
                //.onErrorReturn(throwable -> getFallbackObject())
                ;
    }

    public List<ISuggestionDomain> getFallbackObject() {
        return Collections.EMPTY_LIST;
    }
}
