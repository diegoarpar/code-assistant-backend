package com.co.code.assistant.controllers;

import java.util.List;
import java.util.Map;

public interface IGetController<R> {

    R getInformation(Map<String, List<String>> params);
}
