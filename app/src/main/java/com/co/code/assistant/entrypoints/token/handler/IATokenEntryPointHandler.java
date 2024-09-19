package com.co.code.assistant.entrypoints.token.handler;

import com.co.code.assistant.configurations.IRouter;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.tokencontroller.TokenController;
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

public class IATokenEntryPointHandler implements IRouter<TokenController> {

    private final Javalin javalin;
    private final TokenController controller;

    @Inject
    public IATokenEntryPointHandler(Javalin javalin, TokenController controller) {
        this.javalin = javalin;
        this.controller = controller;
    }

    @Override
    public void bind() {
        javalin.post("/api/token", ctx -> {
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
