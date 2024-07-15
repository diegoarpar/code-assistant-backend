package com.co.code.assistant.configurations;

import com.google.inject.AbstractModule;

public class ApplicationModuleConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        install(new JavalinModule());
        install(new SuggestionModule());
        //install(new PingModule());
    }
}