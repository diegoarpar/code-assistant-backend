package com.co.code.assistant.controllers.feedbackcontroller;

import com.co.code.assistant.controllers.Controller;
import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.IGetController;
import com.co.code.assistant.controllers.feedbackcontroller.body.FeedbackControllerBody;
import com.co.code.assistant.controllers.feedbackcontroller.dto.FeedbackControllerDto;
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
public class FeedbackController extends Controller implements IGetController<Observable<PresenterDto>, IRequestBody> {

    @Inject
    public ISuggestionHandlerController< Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> suggestionHandlerController;
    @Inject
    public SuggestionPresenter presenter;


    public Observable<Object> exampleCreate(Context context, FeedbackControllerBody body) {
        return null;
    }

    @Override
    public Observable<PresenterDto> getInformation(Map<String, List<String>> params, IRequestBody body) {
        if (!System.getenv("suggestionkey").equalsIgnoreCase(((RequestBodyCodeInputDto)body).key)) {
            FeedbackControllerDto controllerDto = new FeedbackControllerDto();
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
