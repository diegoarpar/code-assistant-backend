package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.example.SuggestionController;
import com.co.code.assistant.controllers.example.body.SuggestionControllerBody;
import com.co.code.assistant.controllers.example.handler.SuggestionControllerHandler;
import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.core.usecases.suggestion.SuggestionUseCase;
import com.co.code.assistant.entrypoints.example.dto.IRequestBody;
import com.co.code.assistant.entrypoints.example.handler.IACodeAssitantEntryPointHandler;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.co.code.assistant.providers.items.gpt.GPTProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Map;

public class SuggestionModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    protected IRouter<SuggestionController> provideExampleRouter(IACodeAssitantEntryPointHandler IACodeAssitantEntryPointHandler) {
        return IACodeAssitantEntryPointHandler;
    }

    @Provides
    @Singleton
    protected IGetController<Observable<ControllerDto>> provideExampleController(SuggestionController exampleProvider) {
        return exampleProvider;
    }
    /*
    @Provides
    @Singleton
    protected IUIComponentDecorator<MessageData, UIComponentDto> provideUITextDecorator(UIComponentTextDecorator uiTextDecorator) {
        return uiTextDecorator;
    }
    */
    @Provides
    @Singleton
    protected ISuggestionHandlerController<Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> provideControllerHandler(SuggestionControllerHandler suggestionControllerHandler) {
        return suggestionControllerHandler;
    }

    @Provides
    @Singleton
    protected SuggestionSafeUseCase<ISuggestionDomain, Map<String, List<String>>> getSafeCase(SuggestionUseCase useCase) {
        return useCase;
    }

    @Provides
    @Singleton
    protected ISuggestionRepository<Observable<ISuggestionDto>, Map<String, String>> getRepository(GPTProvider provider) {
        return provider;
    }

}
