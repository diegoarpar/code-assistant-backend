package com.co.code.assistant.entrypoints.feedback.handler;

import com.co.code.assistant.configurations.IRouter;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.feedbackcontroller.FeedbackController;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.codeassitant.dto.RequestBodyCodeInputDto;
import com.co.code.assistant.presenters.PresenterDto;
import com.google.inject.Inject;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

public class IAFeedbackEntryPointHandler implements IRouter<FeedbackController> {

    private final Javalin javalin;
    private final FeedbackController controller;

    @Inject
    public IAFeedbackEntryPointHandler(Javalin javalin, FeedbackController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {
        javalin.post("/api/feedback", ctx -> {
            ctx.future(() -> getCompletableFuture(controller, ctx));
        });

    }

    private CompletableFuture<Void> getCompletableFuture(IGetController<Observable<PresenterDto>, IRequestBody> controller, @NotNull Context ctx) {
        Future future = controller.getInformation(ctx.queryParamMap(), ctx.bodyAsClass(RequestBodyCodeInputDto.class)).doOnNext(controllerDto -> ctx.json(controllerDto)).toFuture();
        return CompletableFuture.supplyAsync(() -> {
            try {
                return future.get();
            } catch (Exception ex) {
                throw new CompletionException(ex);
            } // Or return default value
        }).thenAccept(s -> ctx.json(s));

    }

}
