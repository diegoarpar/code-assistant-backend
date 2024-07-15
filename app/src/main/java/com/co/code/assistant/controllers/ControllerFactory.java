package com.co.code.assistant.controllers;


import com.co.code.assistant.controllers.example.SuggestionController;

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
