package com.snapp.phonebook.exceptions.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //field that has error

    private String errorCode;
    private String errorDescription;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String referenceName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String originalValue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String extraData;

    public FieldErrorDTO() {
    }

    FieldErrorDTO(String objectName, String referenceName, String message, String originalValue, String errorCode, String errorDescription) {
        this.referenceName = referenceName;
        this.errorDescription = errorDescription;
        this.originalValue = originalValue;
        this.errorCode = errorCode;
        this.extraData = "objectName is: " + objectName + " description is: " + message;
    }

    public String getReferenceName() {
        return referenceName;
    }


    public String getExtraData() {
        return extraData;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public FieldErrorDTO setReferenceName(String referenceName) {
        this.referenceName = referenceName;
        return this;
    }

    public FieldErrorDTO setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
        return this;
    }

    public FieldErrorDTO setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    public FieldErrorDTO setExtraData(String extraData) {
        this.extraData = extraData;
        return this;
    }

    public FieldErrorDTO setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    @Override
    public String toString() {
        return "FieldErrorDTO{" +
                "errorCode='" + errorCode + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", referenceName='" + referenceName + '\'' +
                ", originalValue='" + originalValue + '\'' +
                ", extraData='" + extraData + '\'' +
                '}';
    }
}
