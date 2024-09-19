package com.co.code.assistant.presenters.logs;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.presenters.IPresenterExample;
import com.co.code.assistant.presenters.PresenterDto;
import com.co.code.assistant.presenters.logs.dto.LogsPresenterDto;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogsPresenter implements IPresenterExample<LogsPresenterDto> {


    public Observable<LogsPresenterDto> presenter(ControllerDto controllerDto, Map<String, List<String>> params) {
        return Observable.fromCallable(() -> {
            LogsPresenterDto presenterDto = LogsPresenterDto.builder().build();
                    presenterDto.components = controllerDto.results.stream().map(
                            row -> {
                                LogsPresenterDto.PresenterComponentDto componentDto = new LogsPresenterDto.PresenterComponentDto();
                                componentDto.content = row.content
                                        .replaceAll("`", "");
                                componentDto.id = row.id;
                                return componentDto;
                            }
                    ).collect(Collectors.toList());
                    return presenterDto;
                }
        );
    }
}
