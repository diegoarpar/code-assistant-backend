package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.tokencontroller.TokenController;
import com.co.code.assistant.controllers.tokencontroller.handler.TokenControllerHandler;
import com.co.code.assistant.core.domains.implementation.LogDomain;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.core.usecases.token.TokenUseCase;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.token.handler.IATokenEntryPointHandler;
import com.co.code.assistant.presenters.PresenterDto;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

public class TokenModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    protected IRouter<TokenController> provideRouter(IATokenEntryPointHandler IACodeAssitantEntryPointHandler) {
        return IACodeAssitantEntryPointHandler;
    }

    @Provides
    @Singleton
    @Named("token")
    protected IGetController<Observable<PresenterDto>, IRequestBody> provideController(TokenController suggestionController) {
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
    @Named("token")
    protected ISuggestionHandlerController<Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> provideControllerHandler(TokenControllerHandler suggestionControllerHandler) {
        return suggestionControllerHandler;
    }

    @Provides
    @Singleton
    @Named("token")
    protected SuggestionSafeUseCase<List<LogDomain>, Map<String, List<String>>> getSafeCase(TokenUseCase useCase) {
        return useCase;
    }



}
