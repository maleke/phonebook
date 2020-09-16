package com.snapp.phonebook.exceptions.error;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transfering error errorCode with a list of referenceName errors.
 */
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorDescription;

    private List<FieldErrorDTO> errors;

    public ErrorDTO() {
    }

    ErrorDTO(String errorCode) {
        this(errorCode, null);
    }

    public ErrorDTO(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    ErrorDTO(String errorCode, String errorDescription, List<FieldErrorDTO> errors) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errors = errors;
    }

    public void add(String objectName, String referenceName, String message, String originlValue, String errorCode, String errorDescription) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new FieldErrorDTO(objectName, referenceName, message, originlValue, errorCode, errorDescription));
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public List<FieldErrorDTO> getErrors() {
        return errors;
    }
}
