package com.snapp.phonebook.exceptions;


public enum ErrorCodes {

    UNKNOWN("Unknown error", 1),
    INTERNAL_ERROR("Internal error", 6001),
    NOT_IMPLEMENTED("Method not implemented", 6002),

    BAD_INPUT("Input is not correct", 6011),
    MISSING_PARAM("Some parameters are missing.", 6012),

    NO_ENTITY("No entity can be found.", 6021),
    DUPLICATE_ENTRY("Duplicate entry", 6022),
    PROPERTY_REFERENCE_ERROR("Cannot delete because of reference dependency", 6025),

    ACCESS_DENIED("Access is denied.", 6041),
    INACTIVE_USER("User is not active.", 6045),

    NO_USER("User is not found.", 6044),
    MESSAGE_TEMPLATE_NULL("message template is null .", 6046),
    NO_RESPONSE("no response from the provider", 6060),
    DATABASE_CONNECTION_ERROR("Database connectivity error.", 6050);



    private final String message;

    private final int code;

    ErrorCodes(String message, int code) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCodes find(int errorCode) {
        for (ErrorCodes item : ErrorCodes.values()) {
            if (item.code == errorCode)
                return item;
        }
        return UNKNOWN;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
