package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.SuggestionController.SuggestionController;
import com.co.code.assistant.controllers.SuggestionController.handler.SuggestionControllerHandler;
import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.repositories.suggestion.ISuggestionRepository;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.core.usecases.suggestion.SuggestionUseCase;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.codeassitant.handler.IACodeAssitantEntryPointHandler;
import com.co.code.assistant.presenters.PresenterDto;
import com.co.code.assistant.providers.copilot.client.CopilotASuggestionClient;
import com.co.code.assistant.providers.geminis.client.GeminisIASuggestionClient;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.co.code.assistant.providers.openia.client.OpenIASuggestionClient;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Named;
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
    protected IGetController<Observable<PresenterDto>> provideController(SuggestionController suggestionController) {
        return suggestionController;
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
    protected SuggestionSafeUseCase<List<ISuggestionDomain>, Map<String, List<String>>> getSafeCase(SuggestionUseCase useCase) {
        return useCase;
    }

    @Provides
    @Singleton
    @Named("openai")
    protected ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> getRepositoryOpenAi(OpenIASuggestionClient provider) {
        return provider;
    }

    @Provides
    @Singleton
    @Named("openaisummary")
    protected ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> getRepositoryOpenAiSummary(OpenIASuggestionClient provider) {
        return provider;
    }

    @Provides
    @Singleton
    @Named("geminis")
    protected ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> getRepositoryGemenis(GeminisIASuggestionClient provider) {
        return provider;
    }

    @Provides
    @Singleton
    @Named("copilot")
    protected ISuggestionRepository<Observable<ISuggestionDto>, Map<String, List<String>>> getRepositoryCopilot(CopilotASuggestionClient provider) {
        return provider;
    }

}
