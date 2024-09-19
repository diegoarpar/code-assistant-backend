package com.co.code.assistant.entrypoints.token;

import com.co.code.assistant.controllers.tokencontroller.TokenController;
import com.co.code.assistant.entrypoints.IEntryPoints;
import com.google.inject.Inject;
import io.javalin.Javalin;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class TokenEntryPoint implements IEntryPoints {

    private final Javalin javalin;
    private final TokenController controller;

    @Inject
    public TokenEntryPoint(Javalin javalin, TokenController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {

    }

}
