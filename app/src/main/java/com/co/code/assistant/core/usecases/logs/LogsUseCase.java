package com.co.code.assistant.core.usecases.logs;

import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.domains.implementation.LogDomain;
import com.co.code.assistant.core.repositories.database.ISuggestionDatabaseRepository;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.core.usecases.suggestion.model.SuggestionUseCaseModel;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogsUseCase implements SuggestionSafeUseCase<List<LogDomain>, Map<String, List<String>>> {


    private ISuggestionDatabaseRepository<Observable<List<ISuggestionDto>>, Map<String, String>> databaseRepository;

    @Inject
    public LogsUseCase(@Named("mongodb") ISuggestionDatabaseRepository<Observable<List<ISuggestionDto>>, Map<String, String>> databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    public SuggestionUseCaseModel getModel(ISuggestionDomain example2Dto, ISuggestionDomain example3Dto) {
        return SuggestionUseCaseModel.builder().example2Entity(example2Dto).example3Entity(example3Dto).build();
    }


    @Override
    public Observable<List<LogDomain>> fallback(Throwable t) {
        return Observable.just(getFallbackObject());
    }

    @Override
    public Observable<List<LogDomain>> run(Map<String, List<String>> params) {

        return databaseRepository.getInformation(null)
                .flatMap(logs -> {
                    List<LogDomain> list = logs.stream().map(log ->
                            {
                                return LogDomain.builder()
                                        .id(log.getId())
                                        .name(log.getContent())
                                        .build();
                            }
                            ).collect(Collectors.toList());
                    return Observable.just(list);
                });
    }

    public List<LogDomain> getFallbackObject() {
        return Collections.EMPTY_LIST;
    }
}
