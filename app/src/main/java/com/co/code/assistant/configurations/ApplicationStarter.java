package com.co.code.assistant.configurations;

import com.co.code.assistant.controllers.SuggestionController.SuggestionController;
import com.co.code.assistant.controllers.feedbackcontroller.FeedbackController;
import com.co.code.assistant.controllers.logcontroller.LogController;
import com.co.code.assistant.controllers.tokencontroller.TokenController;
import com.google.inject.Inject;
import io.javalin.Javalin;

/**
 * Main class for the App.
 */

public class ApplicationStarter {
    private final Javalin javalin;
    private final IRouter<SuggestionController> suggestionControllerIRouter;
    private final IRouter<LogController> logControllerIRouter;
    private final IRouter<TokenController> tokenControllerIRouter;
    private final IRouter<FeedbackController> feedbackControllerIRouter;

    @Inject
    public ApplicationStarter(Javalin javalin,
                              IRouter<SuggestionController> suggestionControllerIRouter,
                              IRouter<LogController> logControllerIRouter,
                              IRouter<TokenController> tokenControllerIRouter,
                              IRouter<FeedbackController> feedbackControllerIRouter
    ) {
        this.javalin = javalin;
        this.suggestionControllerIRouter = suggestionControllerIRouter;
        this.logControllerIRouter = logControllerIRouter;
        this.tokenControllerIRouter = tokenControllerIRouter;
        this.feedbackControllerIRouter = feedbackControllerIRouter;
        //this.routerPing = pingController;
    }

    public void run(String... args) {
        suggestionControllerIRouter.bind();
        logControllerIRouter.bind();
        tokenControllerIRouter.bind();
        feedbackControllerIRouter.bind();
        javalin.start(3030);
    }
}
