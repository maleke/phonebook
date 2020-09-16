package com.snapp.phonebook.exceptions;

import com.snapp.phonebook.exceptions.error.ErrorCode;
import com.snapp.phonebook.exceptions.error.FieldErrorDTO;

public class ServiceException extends Exception {
    int errorCode;
    private FieldErrorDTO fieldErrorDTO = new FieldErrorDTO();

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(ErrorCode errorCode) {
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

    public ServiceException(FieldErrorDTO fieldErrorDTO) {
        this.fieldErrorDTO = fieldErrorDTO;
    }
    public ServiceException(FieldErrorDTO fieldErrorDTO, String message) {
        this.fieldErrorDTO.setErrorCode(fieldErrorDTO.getErrorCode())
                .setErrorDescription(fieldErrorDTO.getErrorDescription())
                .setReferenceName(fieldErrorDTO.getReferenceName())
                .setOriginalValue(fieldErrorDTO.getOriginalValue())
                .setExtraData(message);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public ServiceException setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public FieldErrorDTO getFieldErrorDTO() {
        return fieldErrorDTO;
    }

    public ServiceException setFieldErrorDTO(FieldErrorDTO fieldErrorDTO) {
        this.fieldErrorDTO = fieldErrorDTO;
        return this;
    }
}
