package com.snapp.phonebook.common;

import java.io.Serializable;

public class CommonResponseDto implements Serializable {

    private RESULT result = RESULT.SUCCESS;
    private String data;
    private int errorCode;

    public RESULT getResult() {
        return result;
    }

    public CommonResponseDto setResult(RESULT result) {
        this.result = result;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public CommonResponseDto setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getData() {
        return data;
    }

    public CommonResponseDto setData(String data) {
        this.data = data;
        return this;
    }

    public enum RESULT {SUCCESS, FAILURE}
}
