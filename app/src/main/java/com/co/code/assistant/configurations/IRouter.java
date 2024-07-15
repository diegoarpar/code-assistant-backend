package com.co.code.assistant.configurations;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

public interface IRouter<I> {
    void bind();

    default <T> CompletableFuture<Void> executeFuture(@NotNull Context ctx, Future<T> future) {
        return CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                return future.get();
                            } catch (Exception ex) {
                                throw new CompletionException(ex);
                            } // Or return default value
                        }
                )
                .thenAccept(ctx::json);
    }
}