package com.co.code.assistant.presenters.suggestion;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.presenters.IPresenterExample;
import com.co.code.assistant.presenters.PresenterDto;
import com.co.code.assistant.presenters.suggestion.dto.SuggestionPresenterDto;
import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SuggestionPresenter implements IPresenterExample {


    public Observable<PresenterDto> presenter(ControllerDto controllerDto, Map<String, List<String>> params) {
        return Observable.fromCallable(() -> {
                    SuggestionPresenterDto presenterDto = SuggestionPresenterDto.builder().build();
                    presenterDto.components = controllerDto.results.stream().map(
                            row -> {
                                SuggestionPresenterDto.PresenterComponentDto componentDto = new SuggestionPresenterDto.PresenterComponentDto();
                                componentDto.content = row.content;
                                componentDto.id = row.id;
                                componentDto.components = Arrays.stream(row.content.split("\\n")).map(s -> {
                                    SuggestionPresenterDto.PresenterComponentDto componentDtoInside = new SuggestionPresenterDto.PresenterComponentDto();
                                    componentDtoInside.content = s;
                                    componentDtoInside.id = "TEXT";
                                    return componentDtoInside;
                                }).toList();
                                return componentDto;
                            }
                    ).collect(Collectors.toList());
                    return presenterDto;
                }
        );
    }
}
