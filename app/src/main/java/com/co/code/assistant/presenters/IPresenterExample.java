package com.co.code.assistant.presenters;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.presenters.suggestion.dto.SuggestionPresenterDto;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Map;

public interface IPresenterExample {

    Observable<PresenterDto> presenter(ControllerDto controllerDto, Map<String, List<String>> params);
}
