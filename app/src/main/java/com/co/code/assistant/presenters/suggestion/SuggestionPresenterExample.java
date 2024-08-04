package com.co.code.assistant.presenters.suggestion;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.presenters.IPresenterExample;
import com.co.code.assistant.presenters.components.text.UIComponentTextDecorator;
import com.co.code.assistant.presenters.suggestion.dto.SuggestionPresenterDto;
import com.google.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Map;

public class SuggestionPresenterExample implements IPresenterExample {

    @Inject
    public UIComponentTextDecorator uiComponentTextDecorator;

    public Observable<ControllerDto> presenter(ControllerDto controllerDto, Map<String, List<String>> params) {
        return Observable.fromCallable(() -> {
                    ControllerDto controllerDto1 = SuggestionPresenterDto.builder().build();
                    controllerDto1.id = controllerDto.id;
                    controllerDto1.status = controllerDto.status;
                    return controllerDto1;
                }
        );
    }
}
