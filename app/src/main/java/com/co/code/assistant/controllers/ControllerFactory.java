package com.co.code.assistant.controllers;


import com.co.code.assistant.controllers.SuggestionController.SuggestionController;

public class ControllerFactory {

    public Controller getController(String device) {
        switch (device) {
            case "exampleController":
                return new SuggestionController();

            default:
                return new SuggestionController();
        }
    }
}
