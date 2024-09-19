package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.SuggestionController.SuggestionController;
import com.co.code.assistant.controllers.logcontroller.LogController;
import com.google.inject.Inject;
import io.javalin.Javalin;

/**
 * Main class for the App.
 */

public class ApplicationStarter {
    private final Javalin javalin;
    private final IRouter<SuggestionController> suggestionControllerIRouter;
    private final IRouter<LogController> logControllerIRouter;

    @Inject
    public ApplicationStarter(Javalin javalin,
                              IRouter<SuggestionController> suggestionControllerIRouter,
                              IRouter<LogController> logControllerIRouter
    ) {
        this.javalin = javalin;
        this.suggestionControllerIRouter = suggestionControllerIRouter;
        this.logControllerIRouter = logControllerIRouter;
        //this.routerPing = pingController;
    }

    public void run(String... args) {
        suggestionControllerIRouter.bind();
        logControllerIRouter.bind();
        javalin.start(3030);
    }
}
