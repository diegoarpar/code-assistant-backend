package com.co.code.assistant.entrypoints.example.handler;

import com.co.code.assistant.configurations.IRouter;
import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.example.SuggestionController;
import com.google.inject.Inject;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

public class ExampleEntryPointHandler implements IRouter<SuggestionController> {

    private final Javalin javalin;
    private final SuggestionController controller;

    @Inject
    public ExampleEntryPointHandler(Javalin javalin, SuggestionController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {
        javalin.get("/api/messages", ctx -> {
            ctx.future(() -> getCompletableFuture(controller, ctx));
        });

    }

    private CompletableFuture<Void> getCompletableFuture(IGetController<Observable<ControllerDto>> controller, @NotNull Context ctx) {
        Future future = controller.getInformation(ctx.queryParamMap()).doOnNext(controllerDto -> ctx.json(controllerDto)).toFuture();
        return CompletableFuture.supplyAsync(() -> {
            try {
                return future.get();
            } catch (Exception ex) {
                throw new CompletionException(ex);
            } // Or return default value
        }).thenAccept(s -> ctx.json(s));

    }

}
