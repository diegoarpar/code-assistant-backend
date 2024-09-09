package com.co.code.assistant.core.usecases.suggestion.handlers;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.domains.implementation.SuggestionDomain;
import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Named;
import java.util.ArrayList;
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

    public Observable<List<ISuggestionDomain>> get(Map<String, List<String>> params) {
        //params.put("prompt", List.of("You are a helpful assistant. Validate only for JAVA code. If the code is JAVA, then use sonarqube rules and PMD rules. Not bring not valuable recommendations. If this is not JAVA, please bring what kind of static code analysis can use. Return the information with html tag"));
        params.put("prompt", List.of("You are a helpful assistant. Validate only for JAVA code. If the code is JAVA, then use sonarqube rules, FindBugs, SpotBugs, Checkstyle, security, and PMD rules. Not bring not valuable recommendations. If this is not JAVA, please bring what kind of static code analysis can use. Answer should be embedded in html tags instead of ** or \\n"));
        List<ISuggestionDomain> list = new ArrayList<>();
        return Observable.zip(clientOpenIA.getInformation(params),
                                clientGeminis.getInformation(params),
                                clientCopilot.getInformation(params),
                                    (openIA, geminis, copilot) -> {
                                        params.put("prompt", List.of("Create a executive summary with all the input information. Answer should be embedded in html tags instead of ** or \\n"));
                                        params.put("code", List.of(String.format("%s %s %s", openIA.getContent(), geminis.getContent(), copilot.getContent())));

                                        ISuggestionDomain openIADomain = SuggestionDomain.builder()
                                                .id("OPENAI")
                                                .name(openIA.getContent())
                                                .build();
                                        list.add(openIADomain);

                                        ISuggestionDomain copilotDomain = SuggestionDomain.builder()
                                                .id("COPILOT")
                                                .name(copilot.getContent())
                                                .build();
                                        list.add(copilotDomain);

                                        ISuggestionDomain geminisDomain = SuggestionDomain.builder()
                                                .id("GEMINIS")
                                                .name(geminis.getContent())
                                                .build();
                                        list.add(geminisDomain);

                                        return clientOpenIASummary.getInformation(params).map(summary -> {
                                                    ISuggestionDomain summaryDomain = SuggestionDomain.builder()
                                                    .id("SUMMARY")
                                                    .name(summary.getContent())
                                                    .build();
                                                    list.add(summaryDomain);
                                                    return list;
                                                }


                                        );
                                    }).flatMap(suggestionDomainObservable -> suggestionDomainObservable);


    }

}
