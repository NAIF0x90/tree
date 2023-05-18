package com.tree.config;

public class BadRequestException extends RuntimeException {

    private int errorCode;
    private String errorMessage;

    public BadRequestException(Throwable throwable) {
        super(throwable);
    }

    public BadRequestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public BadRequestException(String msg) {
        super(msg);
        this.errorMessage = msg;
    }

    public BadRequestException(String message, int errorCode) {
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