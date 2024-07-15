package com.co.code.assistant.providers.items.gpt;

import com.co.code.assistant.providers.items.dto.OpenGptResponseDto;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class OpenGptClientProvider {
    public OpenGptClientProvider() throws IOException {
    }


    public Observable<ISuggestionDto> getInformation(Map<String, String> info) {
        Map<String, String> params = new ConcurrentHashMap<>();
        params.putIfAbsent("client.id", "1234");

        Map<String, String> headers = new ConcurrentHashMap<>();
        headers.putIfAbsent("x-caller-scopes","admin");

        return Observable.just(new OpenGptResponseDto());
    }


}
