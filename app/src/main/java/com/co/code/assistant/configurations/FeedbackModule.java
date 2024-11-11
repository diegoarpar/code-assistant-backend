package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.feedbackcontroller.FeedbackController;
import com.co.code.assistant.controllers.feedbackcontroller.handler.FeedbackControllerHandler;
import com.co.code.assistant.core.domains.implementation.LogDomain;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.core.usecases.feedback.FeedbackUseCase;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.feedback.handler.IAFeedbackEntryPointHandler;
import com.co.code.assistant.presenters.PresenterDto;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

public class FeedbackModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    protected IRouter<FeedbackController> provideRouter(IAFeedbackEntryPointHandler IACodeAssitantEntryPointHandler) {
        return IACodeAssitantEntryPointHandler;
    }

    @Provides
    @Singleton
    @Named("feedback")
    protected IGetController<Observable<PresenterDto>, IRequestBody> provideController(FeedbackController suggestionController) {
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
    @Named("feedback")
    protected ISuggestionHandlerController<Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> provideControllerHandler(FeedbackControllerHandler suggestionControllerHandler) {
        return suggestionControllerHandler;
    }

    @Provides
    @Singleton
    @Named("feedback")
    protected SuggestionSafeUseCase<List<LogDomain>, Map<String, List<String>>> getSafeCase(FeedbackUseCase useCase) {
        return useCase;
    }

}
