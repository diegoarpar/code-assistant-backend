package com.co.code.assistant.core.domains;


public interface ISuggestionDomain {
    String geId();
    String getContent();

    void setId(String id);
    void setContent(String name);

    ISuggestionDomain getEmptyObject();

}
