package com.co.code.assistant.core.repositories.suggestion;


public interface ISuggestionRepository<Response, Params> {

    Response getInformation(Params info);

    void setInformation(Params info);
}
