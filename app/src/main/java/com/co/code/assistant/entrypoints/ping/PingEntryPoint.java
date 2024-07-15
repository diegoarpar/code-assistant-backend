package com.co.code.assistant.entrypoints.ping;

import com.co.code.assistant.controllers.ping.PingController;
import com.co.code.assistant.entrypoints.IEntryPoints;
import com.google.inject.Inject;
import io.javalin.Javalin;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class PingEntryPoint implements IEntryPoints {

    private final Javalin javalin;
    private final PingController controller;

    @Inject
    public PingEntryPoint(Javalin javalin, PingController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {

    }

}
