package com.co.code.assistant.entrypoints.logs.handler;

import com.co.code.assistant.configurations.IRouter;
import com.co.code.assistant.controllers.ControllerBody;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.logcontroller.LogController;
import com.co.code.assistant.controllers.logcontroller.body.LogControllerBody;
import com.co.code.assistant.entrypoints.codeassitant.dto.RequestBodyCodeInputDto;
import com.co.code.assistant.presenters.PresenterDto;
import com.co.code.assistant.presenters.logs.dto.LogsPresenterDto;
import com.google.inject.Inject;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

public class IALogsEntryPointHandler implements IRouter<LogController> {

    private final Javalin javalin;
    private final LogController controller;

    @Inject
    public IALogsEntryPointHandler(Javalin javalin, LogController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {
        javalin.post("/api/log", ctx -> {
            ctx.future(() -> getCompletableFuture(controller, ctx));
        });

    }

    private CompletableFuture<Void> getCompletableFuture(IGetController<Observable<LogsPresenterDto>, LogControllerBody> controller, @NotNull Context ctx) {
        Future future = controller.getInformation(ctx.queryParamMap(), ctx.bodyAsClass(LogControllerBody.class)).doOnNext(controllerDto -> ctx.json(controllerDto)).toFuture();
        return CompletableFuture.supplyAsync(() -> {
            try {
                return future.get();
            } catch (Exception ex) {
                throw new CompletionException(ex);
            } // Or return default value
        }).thenAccept(s -> ctx.json(s));

    }

}
