package com.tree.config;

public class UnauthorizedRequestException extends RuntimeException {

    private int errorCode;
    private String errorMessage;

    public UnauthorizedRequestException(Throwable throwable) {
        super(throwable);
    }

    public UnauthorizedRequestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public UnauthorizedRequestException(String msg) {
        super(msg);
        this.errorMessage = msg;
    }

    public UnauthorizedRequestException(String message, int errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = message;
    }


    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return this.errorCode + " : " + this.getErrorMessage();
    }
}