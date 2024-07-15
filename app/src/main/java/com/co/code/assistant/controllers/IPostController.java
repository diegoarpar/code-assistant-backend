package com.co.code.assistant.controllers;

import java.util.List;
import java.util.Map;

public interface IPostController<B,R> {

    R postInformation(Map<String, List<String>> params, B body);
}
