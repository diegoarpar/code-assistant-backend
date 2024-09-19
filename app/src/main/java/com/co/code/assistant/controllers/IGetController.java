package com.co.code.assistant.controllers;

import com.co.code.assistant.entrypoints.codeassitant.dto.IRequestBody;

import java.util.List;
import java.util.Map;

public interface IGetController<R, B> {

    R getInformation(Map<String, List<String>> params);
    R getInformation(Map<String, List<String>> params, B body);
}
