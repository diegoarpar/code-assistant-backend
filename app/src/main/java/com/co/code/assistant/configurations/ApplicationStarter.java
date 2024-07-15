package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.example.SuggestionController;
import com.google.inject.Inject;
import io.javalin.Javalin;

/**
 * Main class for the App.
 */

public class ApplicationStarter {
    private final Javalin javalin;
    private final IRouter<SuggestionController> exampleControllerIRouter;

    @Inject
    public ApplicationStarter(Javalin javalin,
                              IRouter<SuggestionController> exampleControllerIRouter
    ) {
        this.javalin = javalin;
        this.exampleControllerIRouter = exampleControllerIRouter;
        //this.routerPing = pingController;
    }

    public void run(String... args) {
        exampleControllerIRouter.bind();
        javalin.start(8080);
    }
}
