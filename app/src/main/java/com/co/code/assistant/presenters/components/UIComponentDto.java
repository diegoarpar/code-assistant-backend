package com.co.code.assistant.presenters.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class UIComponentDto {

    @JsonProperty("label_text")
    public String labelText;

    public abstract String getType();
}
