package com.co.code.assistant.controllers.logcontroller.handler;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.logcontroller.body.LogControllerBody;
import com.co.code.assistant.controllers.logcontroller.dto.LogControllerDto;
import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.domains.implementation.LogDomain;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.codeassitant.dto.RequestBodyCodeInputDto;
import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

public class LogControllerHandler implements ISuggestionHandlerController<Map<String, List<String>>, LogControllerBody, Observable<ControllerDto>> {

    private final SuggestionSafeUseCase<List<LogDomain>, Map<String, List<String>>> useCase;

    @Inject
    public LogControllerHandler(SuggestionSafeUseCase<List<LogDomain>, Map<String, List<String>>> useCase) {
        this.useCase = useCase;
    }

    @Override
    public Observable<ControllerDto> handle(Map<String, List<String>> params, LogControllerBody body) {
        Map<String, List<String>> params2 = new HashMap<>();
        List<String> data = new ArrayList<>();

        params2.put("code", data);
        return useCase.run(params2).map(domain -> {
            LogControllerDto dto = new LogControllerDto();
            dto.results = domain.stream().map(d -> {
                ControllerDto.ControllerResultsDto r = new ControllerDto.ControllerResultsDto();
                r.id = d.geId();
                r.content = d.getContent();
                return r;
            }).collect(Collectors.toList());
            dto.setStatus("VISIBLE");
            return dto;
        });
    }

    @Override
    public boolean isValidRequest(Map<String, List<String>> params, LogControllerBody body) {
        return false;
    }
}
