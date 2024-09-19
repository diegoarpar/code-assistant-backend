package com.co.code.assistant.controllers.feedbackcontroller.handler;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.controllers.ISuggestionHandlerController;
import com.co.code.assistant.controllers.feedbackcontroller.dto.FeedbackControllerDto;
import com.co.code.assistant.core.domains.ISuggestionDomain;
import com.co.code.assistant.core.usecases.SuggestionSafeUseCase;
import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;
import com.co.code.assistant.entrypoints.codeassitant.dto.RequestBodyCodeInputDto;
import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

public class FeedbackControllerHandler implements ISuggestionHandlerController<Map<String, List<String>>, IRequestBody, Observable<ControllerDto>> {

    private final SuggestionSafeUseCase<List<ISuggestionDomain>, Map<String, List<String>>> suggestionUseCase;

    @Inject
    public FeedbackControllerHandler(SuggestionSafeUseCase<List<ISuggestionDomain>, Map<String, List<String>>> suggestionUseCase) {
        this.suggestionUseCase = suggestionUseCase;
    }

    @Override
    public Observable<ControllerDto> handle(Map<String, List<String>> params, IRequestBody body) {
        Map<String, List<String>> params2 = new HashMap<>();
        List<String> data = new ArrayList<>();
        data.add(((RequestBodyCodeInputDto) body).code);
        params2.put("code", data);
        return suggestionUseCase.run(params2).map(domain -> {
            FeedbackControllerDto dto = new FeedbackControllerDto();

            dto.setStatus("VISIBLE");
            return dto;
        });
    }

    @Override
    public boolean isValidRequest(Map<String, List<String>> params, IRequestBody body) {
        return false;
    }
}
