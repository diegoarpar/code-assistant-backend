package com.co.code.assistant.controllers.SuggestionController;

import com.co.code.assistant.controllers.Controller;
import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.SuggestionController.body.SuggestionControllerBody;
import com.co.code.assistant.controllers.SuggestionController.dto.SuggestionControllerDto;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.codeassitant.dto.RequestBodyCodeInputDto;
import com.co.code.assistant.presenters.PresenterDto;
import com.co.code.assistant.presenters.suggestion.SuggestionPresenter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;

import javax.naming.Context;
import java.util.List;
import java.util.Map;

@Singleton
public class SuggestionController extends Controller implements IGetController<Observable<PresenterDto>> {

    @Inject
    public ISuggestionHandlerController< Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> suggestionHandlerController;
    @Inject
    public SuggestionPresenter presenter;


    public Observable<Object> exampleCreate(Context context, SuggestionControllerBody body) {
        return null;
    }

    @Override
    public Observable<PresenterDto> getInformation(Map<String, List<String>> params, IRequestBody body) {
        if (!System.getenv("suggestionkey").equalsIgnoreCase(((RequestBodyCodeInputDto)body).key)) {
            SuggestionControllerDto controllerDto = new SuggestionControllerDto();
            controllerDto.setId("N/A");
            return presenter.presenter(controllerDto, params);
        }
        return suggestionHandlerController.handle(params, body).flatMap(controllerDto ->
        {
            return presenter.presenter(controllerDto, params);
        });
    }

    @Override
    public Observable<PresenterDto> getInformation(Map<String, List<String>> params) {
        return null;
    }
}
