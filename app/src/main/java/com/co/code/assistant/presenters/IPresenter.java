package com.co.code.assistant.presenters;

import com.co.code.assistant.controllers.ControllerDto;
import io.reactivex.rxjava3.core.Observable;

public interface IPresenter<P extends ControllerDto, R> {

    Observable<R> presenter(P controllerDto);
}
