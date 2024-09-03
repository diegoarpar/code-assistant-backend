package com.co.code.assistant.controllers.SuggestionController.handler;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.SuggestionController.dto.SuggestionControllerDto;
import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.codeassitant.dto.RequestBodyCodeInputDto;
import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

public class SuggestionControllerHandler implements ISuggestionHandlerController<Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> {

    private final SuggestionSafeUseCase<List<ISuggestionDomain>, Map<String, List<String>>> suggestionUseCase;

    @Inject
    public SuggestionControllerHandler(SuggestionSafeUseCase<List<ISuggestionDomain>, Map<String, List<String>>> suggestionUseCase) {
        this.suggestionUseCase = suggestionUseCase;
    }

    @Override
    public Observable<ControllerDto> handle(Map<String, List<String>> params, IRequestBody body) {
        Map<String, List<String>> params2 = new HashMap<>();
        List<String> data = new ArrayList<>();
        data.add(((RequestBodyCodeInputDto) body).code);
        params2.put("code", data);
        return suggestionUseCase.run(params2).map(domain -> {
            SuggestionControllerDto dto = new SuggestionControllerDto();
            dto.results = domain.stream().map(row -> {
                SuggestionControllerDto.ControllerResultsDto resultsDto = new SuggestionControllerDto.ControllerResultsDto();
                resultsDto.id = row.geId();
                resultsDto.content = row.getContent();
                return resultsDto;
            }).collect(Collectors.toList());

            dto.setStatus("VISIBLE");
            return dto;
        });
    }

    @Override
    public boolean isValidRequest(Map<String, List<String>> params, IRequestBody body) {
        return false;
    }
}
