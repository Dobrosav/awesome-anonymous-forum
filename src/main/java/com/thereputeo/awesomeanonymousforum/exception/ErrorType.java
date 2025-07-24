package com.thereputeo.awesomeanonymousforum.exception;

public enum ErrorType {

    UNRESOLVED_ERROR("100", "General error"),
    NOT_SUPPORTED_HTTP_METHOD("101", "Not supported http method"),
    NOT_VALID_REQUEST_FORMAT("103", "Not valid request format"),
    INVALID_ARGUMENT("104", "Invalid fields in the request"),
    NOT_FOUND("105", "Not found"),
    CLIENT_NOT_AVAILABLE("106", "Client not available");

    private final String code;
    private final String message;

    ErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}