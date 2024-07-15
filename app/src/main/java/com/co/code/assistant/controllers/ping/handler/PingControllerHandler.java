package com.co.code.assistant.controllers.ping.handler;

import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.ping.body.PingControllerBody;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Map;

public class PingControllerHandler implements ISuggestionHandlerController<Map<String, List<String>>, PingControllerBody, Observable<String> > {


    @Override
    public Observable<String> handle(Map<String, List<String>> params, PingControllerBody body) {
        return Observable.just("pong");
    }

    @Override
    public boolean isValidRequest(Map<String, List<String>> params, PingControllerBody body) {
        return false;
    }
}
