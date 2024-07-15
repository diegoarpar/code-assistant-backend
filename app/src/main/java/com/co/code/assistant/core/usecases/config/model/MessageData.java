package com.co.code.assistant.core.usecases.config.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MessageData {
    private String role;
    private String username;
    private Boolean ack;
    private String agentId;
    private String time;
    private String date;
    private String idMessage;

}


