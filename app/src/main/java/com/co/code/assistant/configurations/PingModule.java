package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.ping.PingController;
import com.co.code.assistant.controllers.ping.body.PingControllerBody;
import com.co.code.assistant.controllers.ping.handler.PingControllerHandler;
import com.co.code.assistant.entrypoints.ping.handler.PingEntryPointHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import java.util.Map;

public class PingModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    protected IRouter<PingController> provideExampleRouter(PingEntryPointHandler entryPointHandler) {
        return entryPointHandler;
    }

    @Provides
    @Singleton
    protected ISuggestionHandlerController<Map<String, List<String>>, PingControllerBody, Observable<String> > provideControllerHandler(PingControllerHandler controllerHandler) {
        return controllerHandler;
    }

}
