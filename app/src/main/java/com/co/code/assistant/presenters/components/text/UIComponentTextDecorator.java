package com.co.code.assistant.presenters.components.text;

import com.co.code.assistant.core.usecases.config.model.MessageData;
import com.co.code.assistant.presenters.components.IUIComponentDecorator;
import com.co.code.assistant.presenters.components.UIComponentDto;
import com.co.code.assistant.presenters.components.text.android.UIComponentTextAndroidDto;
import com.co.code.assistant.presenters.components.text.dto.UIComponentTextDto;
import com.co.code.assistant.presenters.components.text.ios.UIComponentTextIosDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UIComponentTextDecorator implements IUIComponentDecorator<MessageData, UIComponentDto> {

    public static final String ANDROID = "android";
    public static final String IOS = "ios";

    @Override
    public UIComponentDto getComponent(MessageData messageData, String device) {

        UIComponentTextAndroidDto androidComponente = getUiComponentAndroidTextDto(device);
        if (androidComponente != null) {
            return androidComponente;
        }

        UIComponentTextIosDto iosComponent = getUiComponentTextIosDto(device);
        if (iosComponent != null) {
            return iosComponent;
        }

        return getUiComponentTextWeb(messageData);
    }

    private UIComponentDto getUiComponentTextWeb(MessageData messageData) {
        UIComponentTextDto webComponent = new UIComponentTextDto();
        webComponent.type = "LABEL";
        webComponent.subtype = "DEFAULT";
        webComponent.interactions = Arrays.asList("swipe", "long-press");
        return webComponent;
    }

    @Nullable
    private static UIComponentTextIosDto getUiComponentTextIosDto(String device) {
        if (IOS.equals(device)) {
            UIComponentTextIosDto iosComponent = new UIComponentTextIosDto();
            iosComponent.text = "Hey, I'm a text component";
            iosComponent.device = "ios";
            return iosComponent;
        }
        return null;
    }

    @Nullable
    private static UIComponentTextAndroidDto getUiComponentAndroidTextDto(String device) {
        if (ANDROID.equals(device)) {
            UIComponentTextAndroidDto androidComponente =
                    new UIComponentTextAndroidDto();
            androidComponente.text = "Hey, I'm a text component";
            androidComponente.device = "android";

            return androidComponente;
        }
        return null;
    }

}
