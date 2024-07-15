package com.co.code.assistant.core.usecases;


import io.reactivex.rxjava3.core.Observable;

public interface SuggestionSafeUseCase<R, P> {


    Observable<R> fallback(Throwable t);

    Observable<R> run(P t);

    R getFallbackObject();
}
