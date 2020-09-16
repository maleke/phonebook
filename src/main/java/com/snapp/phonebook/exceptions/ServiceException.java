package com.snapp.phonebook.exceptions;


public class ServiceException extends Exception {

    int errorCode;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(ErrorCodes errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, int code) {
        super(message);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public ServiceException setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
