package com.co.code.assistant.controllers.ping;

import com.co.code.assistant.controllers.Controller;
import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.SuggestionController.body.SuggestionControllerBody;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Map;

@Singleton
public class PingController extends Controller implements IGetController<Observable<String>> {

    @Inject
    public ISuggestionHandlerController<Map<String, List<String>>, SuggestionControllerBody, Observable<ControllerDto>> exampleControllerHandler;

    @Override
    public Observable<String> getInformation(Map<String, List<String>> params) {
        exampleControllerHandler.handle(params, null);
        return Observable.just("pong");
    }

    @Override
    public Observable<String> getInformation(Map<String, List<String>> params, IRequestBody body) {
        return null;
    }
}
