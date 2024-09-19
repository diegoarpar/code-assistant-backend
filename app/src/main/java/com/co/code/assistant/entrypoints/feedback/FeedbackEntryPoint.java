package com.co.code.assistant.entrypoints.feedback;

import com.co.code.assistant.controllers.feedbackcontroller.FeedbackController;
import com.co.code.assistant.entrypoints.IEntryPoints;
import com.google.inject.Inject;
import io.javalin.Javalin;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
public class FeedbackEntryPoint implements IEntryPoints {

    private final Javalin javalin;
    private final FeedbackController controller;

    @Inject
    public FeedbackEntryPoint(Javalin javalin, FeedbackController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {

    }

}
