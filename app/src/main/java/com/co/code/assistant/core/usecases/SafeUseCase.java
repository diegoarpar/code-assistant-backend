package com.co.code.assistant.core.usecases;


import io.reactivex.rxjava3.core.Observable;

public interface SafeUseCase<R, P> {


    Observable<R> run(P request);

    Observable<R> fallback(Throwable t);

    R getFallbackObject();
}
