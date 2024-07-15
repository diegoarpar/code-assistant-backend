package com.co.code.assistant.entrypoints.example;

import com.co.code.assistant.controllers.example.SuggestionController;
import com.co.code.assistant.entrypoints.IEntryPoints;
import com.google.inject.Inject;
import io.javalin.Javalin;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class ExampleEntryPoint implements IEntryPoints {

    private final Javalin javalin;
    private final SuggestionController controller;

    @Inject
    public ExampleEntryPoint(Javalin javalin, SuggestionController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {

    }

}
