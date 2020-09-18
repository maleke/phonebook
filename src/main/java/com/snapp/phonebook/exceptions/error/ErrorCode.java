package com.snapp.phonebook.exceptions.error;

import java.util.List;

public enum ErrorCode {

    GENERAL_ERROR("Unknown error", 0),
    REQUIRED_FIELD_NULL_VALUE("عدم ارسال مقدار برای فیلد اجباری",1),
    INTERNAL_ERROR("خطای داخلی سرویس دهنده" , 2),
    DUPLICATE_DATA("داده تکراری است",3),
    DATA_MISMATCH("عدم همخوانی داده های ارائه شده", 4),
    DATA_MISMATCH_WITH_PROTOCOL("عدم همخوانی داده های ارائه شده  با قراردادهای سرویس", 5);


    private final String message;
    private final int code;
    private List<String> errors;

    ErrorCode(String message, int code) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode find(int errorCode) {
        for (ErrorCode item : ErrorCode.values()) {
            if (item.code == errorCode)
                return item;
        }
        return GENERAL_ERROR;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
