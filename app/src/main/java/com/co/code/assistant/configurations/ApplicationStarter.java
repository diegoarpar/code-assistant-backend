package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.SuggestionController.SuggestionController;
import com.google.inject.Inject;
import io.javalin.Javalin;

/**
 * Main class for the App.
 */

public class ApplicationStarter {
    private final Javalin javalin;
    private final IRouter<SuggestionController> suggestionControllerIRouter;

    @Inject
    public ApplicationStarter(Javalin javalin,
                              IRouter<SuggestionController> exampleControllerIRouter
    ) {
        this.javalin = javalin;
        this.suggestionControllerIRouter = exampleControllerIRouter;
        //this.routerPing = pingController;
    }

    public void run(String... args) {
        suggestionControllerIRouter.bind();
        javalin.start(3030);
    }
}
