package com.co.code.assistant.entrypoints.logs;

import com.co.code.assistant.controllers.logcontroller.LogController;
import com.co.code.assistant.entrypoints.IEntryPoints;
import com.google.inject.Inject;
import io.javalin.Javalin;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class LogsEntryPoint implements IEntryPoints {

    private final Javalin javalin;
    private final LogController controller;

    @Inject
    public LogsEntryPoint(Javalin javalin, LogController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {

    }

}
