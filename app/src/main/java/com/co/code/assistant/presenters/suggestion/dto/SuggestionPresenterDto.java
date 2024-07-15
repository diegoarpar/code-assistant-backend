package com.co.code.assistant.presenters.suggestion.dto;

import com.co.code.assistant.controllers.ControllerDto;
import com.co.code.assistant.presenters.components.UIComponentDto;
import lombok.Builder;
import java.util.List;

@Builder
public class SuggestionPresenterDto extends ControllerDto {
    private String id;
    private List<UIComponentDto> timeline;
}

