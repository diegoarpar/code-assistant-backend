package com.co.code.assistant.presenters.components.text.dto;

import com.co.code.assistant.presenters.components.UIComponentDto;
import com.co.code.assistant.presenters.components.text.shared.ValueDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UIComponentTextDto extends UIComponentDto {

    public String type;
    public String subtype;
    public String experiments;
    public List<String> interactions;
    @JsonProperty("label_text")
    public String labelText;
    public List<ValueDto> value;

    @Override
    public String getType() {
        return type;
    }
}
