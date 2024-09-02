package com.co.code.assistant.entrypoints.codeassitant;

import com.co.code.assistant.controllers.SuggestionController.SuggestionController;
import com.co.code.assistant.entrypoints.IEntryPoints;
import com.google.inject.Inject;
import io.javalin.Javalin;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class CodeAssistantEntryPoint implements IEntryPoints {

    private final Javalin javalin;
    private final SuggestionController controller;

    @Inject
    public CodeAssistantEntryPoint(Javalin javalin, SuggestionController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {

    }

}
