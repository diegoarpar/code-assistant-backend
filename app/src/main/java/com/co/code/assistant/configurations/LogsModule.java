package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.logcontroller.LogController;
import com.co.code.assistant.controllers.logcontroller.body.LogControllerBody;
import com.co.code.assistant.controllers.logcontroller.handler.LogControllerHandler;
import com.co.code.assistant.core.domains.implementation.LogDomain;
import com.co.code.assistant.core.repositories.database.ISuggestionDatabaseRepository;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.core.usecases.logs.LogsUseCase;
import com.co.code.assistant.entrypoints.logs.handler.IALogsEntryPointHandler;
import com.co.code.assistant.presenters.logs.dto.LogsPresenterDto;
import com.co.code.assistant.providers.database.client.MemoryDatabase;
import com.co.code.assistant.providers.database.client.MongoDatabase;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

public class LogsModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    protected IRouter<LogController> provideRouter(IALogsEntryPointHandler IACodeAssitantEntryPointHandler) {
        return IACodeAssitantEntryPointHandler;
    }

    @Provides
    @Singleton
    protected IGetController<Observable<LogsPresenterDto>, LogControllerBody> provideController(LogController suggestionController) {
        return suggestionController;
    }

    @Provides
    @Singleton
    protected ISuggestionHandlerController<Map<String, List<String>>, LogControllerBody, Observable<ControllerDto>> provideControllerHandler(LogControllerHandler suggestionControllerHandler) {
        return suggestionControllerHandler;
    }

    @Provides
    @Singleton
    @Named("memorydb")
    protected ISuggestionDatabaseRepository<Observable<List<ISuggestionDto>>, Map<String, String>> getDataBaseMemory(MemoryDatabase useCase) {
        return useCase;
    }

    @Provides
    @Singleton
    @Named("mongodb")
    protected ISuggestionDatabaseRepository<Observable<List<ISuggestionDto>>, Map<String, String>> getDataBaseMongo(MongoDatabase useCase) {
        return useCase;
    }

    @Provides
    @Singleton
    protected SuggestionSafeUseCase<List<LogDomain>, Map<String, List<String>>> getSafeCase(LogsUseCase useCase) {
        return useCase;
    }

}
