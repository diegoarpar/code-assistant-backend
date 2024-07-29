package com.co.code.assistant.controllers.example.handler;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.example.body.SuggestionControllerBody;
import com.co.code.assistant.controllers.example.dto.SuggestionControllerDto;
import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.entrypoints.example.dto.IRequestBody;
import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

public class SuggestionControllerHandler implements ISuggestionHandlerController<Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> {

    private final SuggestionSafeUseCase<ISuggestionDomain, Map<String, List<String>>> exampleUseCase;

    @Inject
    public SuggestionControllerHandler(SuggestionSafeUseCase<ISuggestionDomain, Map<String, List<String>>> exampleUseCase) {
        this.exampleUseCase = exampleUseCase;
    }

    @Override
    public Observable<ControllerDto> handle(Map<String, List<String>> params, IRequestBody body) {
        return exampleUseCase.run(params).map(iExampleDomain -> {
            SuggestionControllerDto dto = new SuggestionControllerDto();
            dto.setStatus(iExampleDomain.getExampleId());
            return dto;
        });
    }

    @Override
    public boolean isValidRequest(Map<String, List<String>> params, IRequestBody body) {
        return false;
    }
}
