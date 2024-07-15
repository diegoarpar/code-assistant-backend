package com.co.code.assistant.presenters.components.text.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionsParamsDto {
    public String url;
    public String track_info;
}
