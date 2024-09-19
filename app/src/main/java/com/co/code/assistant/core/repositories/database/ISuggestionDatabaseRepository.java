package com.co.code.assistant.core.repositories.database;


public interface ISuggestionDatabaseRepository<Response, Params> {

    Response getInformation(Params info);

    void setInformation(Params info);
}
