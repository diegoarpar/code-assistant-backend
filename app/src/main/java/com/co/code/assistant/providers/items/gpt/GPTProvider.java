package com.co.code.assistant.providers.items.gpt;

import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

@Singleton
public class GPTProvider implements ISuggestionRepository<Observable<ISuggestionDto>, Map<String, String>> {

    @Inject
    public OpenGptClientProvider openGptClientProvider;

    @Override
    public Observable<ISuggestionDto> getInformation(Map<String, String> info) {
        return openGptClientProvider.getInformation(info);
    }
    @Override
    public void setInformation(Map<String, String> map) {

    }
}
