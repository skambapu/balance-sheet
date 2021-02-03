package com.bian.statement.exception;

import java.text.MessageFormat;

public class ErrorDto {


    private String code;

    private String message;

    private String correlationId;

    public ErrorDto() {
    }

    public ErrorDto(String correlationId, String code, String message, Object... arguments) {
        this.correlationId = correlationId;
        this.code = code;
        this.message = this.getMessage(message, arguments);
    }

    public final String getMessage(String messagePattern, Object... arguments) {
        return MessageFormat.format(messagePattern, arguments);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
