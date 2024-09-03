package com.co.code.assistant.controllers.SuggestionController.dto;

import com.co.code.assistant.controllers.ControllerDto;

import java.util.List;

public class SuggestionControllerDto extends ControllerDto {

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
