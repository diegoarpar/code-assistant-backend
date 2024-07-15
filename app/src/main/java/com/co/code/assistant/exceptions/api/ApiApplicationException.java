package com.co.code.assistant.exceptions.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

public class ApiApplicationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4268772200879302362L;

    @Setter
    @Getter
    protected String errorCode;
    @Getter
    protected Integer status;
    protected String message;

    public ApiApplicationException(Throwable cause) {
        super(cause);
        this.errorCode = "unexpected_error";
        this.status = 500;
    }

    public ApiApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "unexpected_error";
        this.status = 500;
    }

    public ApiApplicationException(Integer status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ApiApplicationException(String errorCode, Integer status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.message = message;
    }

    public static ApiApplicationException wrapIfNotApiApplicationException(Throwable throwable) {
        return throwable instanceof ApiApplicationException ? (ApiApplicationException) throwable : new ApiApplicationException(throwable);
    }

    public static ApiApplicationException wrapIfNotApiApplicationException(String message, Throwable throwable) {
        return throwable instanceof ApiApplicationException ? (ApiApplicationException) throwable : new ApiApplicationException(message, throwable);
    }

    public static Throwable getNotRxJavaExceptionCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        return cause != null && !isRxJavaException(cause) ? getNotRxJavaExceptionCause(cause) : throwable;
    }

    public static Throwable getCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        return cause != null ? cause : throwable;
    }

    private static boolean isRxJavaException(Throwable t) {
        if (t == null) {
            return false;
        } else {
            StackTraceElement[] stackTrace = t.getStackTrace();
            return stackTrace.length > 0 && stackTrace[0].getClassName().startsWith("rx.exceptions");
        }
    }
}
