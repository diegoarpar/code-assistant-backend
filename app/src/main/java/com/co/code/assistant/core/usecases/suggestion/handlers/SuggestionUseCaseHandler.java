package com.co.code.assistant.core.usecases.suggestion.handlers;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.domains.implementation.SuggestionDomain;
import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SuggestionUseCaseHandler {


    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientGeminis;
    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientOpenIA;
    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientOpenIASummary;
    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientCopilot;

    @Inject
    public SuggestionUseCaseHandler(@Named("geminis") ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientGeminis,
                                    @Named("openai") ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientOpenIA,
                                    @Named("openaisummary") ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientOpenIASummary,
                                    @Named("copilot") ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientCopilot) {
        this.clientGeminis = clientGeminis;
        this.clientOpenIA = clientOpenIA;
        this.clientOpenIASummary = clientOpenIASummary;
        this.clientCopilot = clientCopilot;
    }

    public Observable<ISuggestionDomain> get(Map<String, List<String>> params) {
        params.put("prompt", List.of("You are a helpful assistan. Validate only if contain a java code and bring PMD recomendations"));
        return Observable.zip(clientOpenIA.getInformation(params),
                                clientGeminis.getInformation(params),
                                clientCopilot.getInformation(params),
                                    (openIA, geminis, copilot) -> {
                                        params.put("prompt", List.of("Create a executive summary"));
                                        params.put("code", List.of(String.format("%s %s %s", openIA.getId(), geminis.getId(), copilot.getId())));
                                        return clientOpenIASummary.getInformation(params).map(summary -> {
                                                    return SuggestionDomain.builder()
                                                            .exampleId(summary.getId())
                                                            .build();
                                                }
                                        );
                                    }).flatMap(suggestionDomainObservable -> suggestionDomainObservable);


    }

}
