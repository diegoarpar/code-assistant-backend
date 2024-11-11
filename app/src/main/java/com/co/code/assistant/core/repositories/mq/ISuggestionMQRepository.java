package com.co.code.assistant.core.repositories.mq;

public interface ISuggestionMQRepository<Response, Params> {

    void setInformation(Params info);
}
