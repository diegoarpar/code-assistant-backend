package com.co.code.assistant.presenters.components.text.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueDto {
    public String type;
    @JsonProperty("id_icon")
    public String idIcon;
    @JsonProperty("label_text")
    public String labeltext;
    @JsonProperty("action_id")
    public String actionId;
    @JsonProperty("actions_params")
    public ActionsParamsDto actionsParamsDto;
    public String key;
    @JsonProperty("currency_symbol")
    public String currencySymbol;
    public String decimal;
}
