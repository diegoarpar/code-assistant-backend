package com.co.code.assistant.controllers.logcontroller;

import com.co.code.assistant.controllers.Controller;
import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.logcontroller.body.LogControllerBody;
import com.co.code.assistant.controllers.logcontroller.dto.LogControllerDto;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.codeassitant.dto.RequestBodyCodeInputDto;
import com.co.code.assistant.presenters.PresenterDto;
import com.co.code.assistant.presenters.logs.LogsPresenter;
import com.co.code.assistant.presenters.logs.dto.LogsPresenterDto;
import com.co.code.assistant.presenters.suggestion.SuggestionPresenter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.naming.Context;
import java.util.List;
import java.util.Map;

@Singleton
public class LogController extends Controller implements IGetController<Observable<LogsPresenterDto>, LogControllerBody> {

    @Inject
    public ISuggestionHandlerController< Map<String, List<String>>, LogControllerBody, Observable<ControllerDto>> suggestionHandlerController;
    @Inject
    public LogsPresenter presenter;


    public Observable<Object> exampleCreate(Context context, LogControllerDto body) {
        return null;
    }

    @Override
    public Observable<LogsPresenterDto> getInformation(Map<String, List<String>> params, LogControllerBody body) {

        return suggestionHandlerController.handle(params, body).flatMap(controllerDto ->
        {
            return presenter.presenter(controllerDto, params);
        });
    }

    @Override
    public Observable<LogsPresenterDto> getInformation(Map<String, List<String>> params) {
        return null;
    }
}
