package com.co.code.assistant.presenters;

import java.util.List;

@SuppressWarnings({"PMD.AbstractClassWithoutAnyMethod", "PMD.EmptyMethodInAbstractClassShouldBeAbstract", "PMD.AbstractClassWithoutAbstractMethod"})
public abstract class PresenterDto {

    public String status;
    public String id;
    public String content;

    public List<PresenterComponentDto> components;

    public static class PresenterComponentDto {
        public String id;
        public String content;
        public List<PresenterComponentDto> components;
    }

}
