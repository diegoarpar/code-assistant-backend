package com.co.code.assistant.providers.database.client;

import com.co.code.assistant.core.repositories.database.ISuggestionDatabaseRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.co.code.assistant.providers.items.dto.SuggestionDto;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class MemoryDatabase implements ISuggestionDatabaseRepository<Observable<List<ISuggestionDto>>, Map<String, String>> {

    private ConcurrentHashMap<String, String> database = new ConcurrentHashMap();

    @Override
    public Observable<List<ISuggestionDto>> getInformation(Map<String, String> info) {
        return Observable.fromCallable(() -> {
            return database
                    .entrySet()
                    .stream()
                    .map(keyset -> SuggestionDto.builder().content(keyset.getValue()).build())
                    .collect(Collectors.toList());
        });
    }

    @Override
    public void setInformation(Map<String, String> info) {
        info
                .entrySet()
                .stream()
                .forEach(keyset -> database.put(keyset.getKey(), keyset.getValue()));

    }
}
