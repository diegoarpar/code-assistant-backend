package com.co.code.assistant.controllers.example;

import com.co.code.assistant.controllers.Controller;
import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.example.body.SuggestionControllerBody;
import com.co.code.assistant.entrypoints.example.dto.IRequestBody;
import com.co.code.assistant.presenters.suggestion.SuggestionPresenterExample;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.naming.Context;
import java.util.List;
import java.util.Map;

@Singleton
public class SuggestionController extends Controller implements IGetController<Observable<ControllerDto>> {

    @Inject
    public ISuggestionHandlerController< Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> exampleControllerHandler;
    @Inject
    public SuggestionPresenterExample presenter;


    public Observable<Object> exampleCreate(Context context, SuggestionControllerBody body) {
        return null;
    }

    @Override
    public Observable<ControllerDto> getInformation(Map<String, List<String>> params, IRequestBody body) {
        return exampleControllerHandler.handle(params, body).flatMap(controllerDto ->
        {
            return presenter.presenter(controllerDto, params);
        });
    }

    @Override
    public Observable<ControllerDto> getInformation(Map<String, List<String>> params) {
        return null;
    }
}
