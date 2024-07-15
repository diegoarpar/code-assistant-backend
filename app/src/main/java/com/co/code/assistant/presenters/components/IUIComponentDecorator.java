package com.co.code.assistant.presenters.components;

public interface IUIComponentDecorator<P, R> {
    R getComponent(P controllerDto, String device);
}
