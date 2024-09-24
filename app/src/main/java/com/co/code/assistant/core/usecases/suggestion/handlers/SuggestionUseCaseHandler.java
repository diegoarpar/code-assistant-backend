package com.co.code.assistant.core.usecases.suggestion.handlers;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.domains.implementation.SuggestionDomain;
import com.co.code.assistant.core.repositories.database.ISuggestionDatabaseRepository;
import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SuggestionUseCaseHandler {


    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientGeminis;
    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientOpenIA;
    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientOpenIASummary;
    public ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientCopilot;

    private ISuggestionDatabaseRepository<Observable<List<ISuggestionDto>>, Map<String, String>> databaseRepository;


    @Inject
    public SuggestionUseCaseHandler(@Named("geminis") ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientGeminis,
                                    @Named("openai") ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientOpenIA,
                                    @Named("openaisummary") ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientOpenIASummary,
                                    @Named("copilot") ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> clientCopilot,
                                    ISuggestionDatabaseRepository<Observable<List<ISuggestionDto>>, Map<String, String>> databaseRepository) {
        this.clientGeminis = clientGeminis;
        this.clientOpenIA = clientOpenIA;
        this.clientOpenIASummary = clientOpenIASummary;
        this.clientCopilot = clientCopilot;
        this.databaseRepository = databaseRepository;
    }

    public Observable<List<ISuggestionDomain>> get(Map<String, List<String>> params) {
        //params.put("prompt", List.of("You are a helpful assistant. Validate only for JAVA code. If the code is JAVA, then use sonarqube rules and PMD rules. Not bring not valuable recommendations. If this is not JAVA, please bring what kind of static code analysis can use. Return the information with html tag"));
        params.put("prompt", List.of("You are a helpful code java assistant. Validate only for JAVA code. The user will bring a Java code. If the code is JAVA, then use sonarqube rules, Checkstyle rules, and PMD rules to validate the code that the user brings. Mention the class name, method, variable name, or import with the issue separated by sections called PMD issues, Sonarqube issues and Checkstyle issues. If this is not JAVA, please bring what kind of static code analysis can use. Bring the answer in html format tags instead of ** or \\n."));
        params.put("promptgeminis", List.of("You are a helpful code java assistant. Validate only for JAVA code. The user will bring a Java code. If the code is JAVA, then use sonarqube rules, Checkstyle rules, and PMD rules to validate the code that the user brings. Mention the class name, method, variable name, or import with the issue separated by sections called PMD issues, Sonarqube issues and Checkstyle issues. If this is not JAVA, please bring what kind of static code analysis can use. Bring the answer in html format tags instead of ** or \\n."));
        List<ISuggestionDomain> list = new ArrayList<>();
        return Observable.zip(clientOpenIA.getInformation(params),
                                clientGeminis.getInformation(params),
                                clientCopilot.getInformation(params),
                                    (openIA, geminis, copilot) -> {
                                        params.put("prompt", List.of("Create a executive summary with all the information divided by 3 sections. One for PMD, Second for Checkstyle and third one with SonarQube. Bring the answer in html format tags instead of ** or \\n-"));
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
                                                    databaseRepository.setInformation(Map.of(UUID.randomUUID().toString(), String.format("OPENIA: %s GEMINIS: %s COPILOT: %s  SUMMARY%s", openIA.getContent(), geminis.getContent(), copilot.getContent(), summary.getContent())));
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
