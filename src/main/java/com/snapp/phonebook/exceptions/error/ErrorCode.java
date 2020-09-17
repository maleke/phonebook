package com.snapp.phonebook.exceptions.error;

import java.util.List;

public enum ErrorCode {

    GENERAL_ERROR("Unknown error", 0),
    REQUIRED_FIELD_NULL_VALUE("عدم ارسال مقدار برای فیلد اجباری",1),
    WRONG_TEMPLATE("قالب منطقی داده ارسالی نادرست است",2),
    DUPLICATE_DATA("داده تکراری است",3),
    DATA_MISMATCH("عدم همخوانی داده های ارائه شده", 4),
    DATA_MISMATCH_WITH_PROTOCOL("عدم همخوانی داده های ارائه شده  با قراردادهای سرویس", 5),
    LACK_OF_DATA("عدم وجود داده مورد اشاره", 6),
    LACK_OF_SOURCE("عدم وجود منابع کافی جهت تکیل درخواست ", 7),
    LACK_OF_CLIENT_ACCESS_TO_DATA("عدم دسترسی سرویس گیرنده به داده مورد ارجاع", 8),
    SERVICE_CALL_NOT_POSSIBLE("امکان فراخوانی سرویس به صورت موقت وجود ندارد", 10),
    INTERNAL_ERROR("خطای داخلی سرویس دهنده" , 11),
    SERVICE_ACCESS_NOT_AVAILABLE("عدم دسترسی موقتی به سرویس بیرونی", 12),
    SERVICE_NOT_RESPONSE("عدم دریافت پاسخ از سرویس بیرونی", 13),
    RESEND_REQUEST("نیاز به ارسال مجدد درخواست می باشد", 14),
    SERVICE_NOT_FOUND("عدم امکان ارائه سرویس درخواستی", 15),
    DATA_SIZE_ERROR("داده خارج از محدوده مورد قبول", 17);

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
