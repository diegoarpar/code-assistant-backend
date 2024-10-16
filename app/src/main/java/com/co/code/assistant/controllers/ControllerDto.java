package com.co.code.assistant.controllers;

import java.util.List;

@SuppressWarnings({"PMD.AbstractClassWithoutAnyMethod", "PMD.EmptyMethodInAbstractClassShouldBeAbstract", "PMD.AbstractClassWithoutAbstractMethod"})
public abstract class ControllerDto {

    public String status;
    public String id;
    public String content;

    public List<ControllerResultsDto> results;

    public static class ControllerResultsDto {
        public String id;
        public String content;
    }

}
