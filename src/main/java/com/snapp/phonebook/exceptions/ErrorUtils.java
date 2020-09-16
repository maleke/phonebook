package com.snapp.phonebook.exceptions;

import com.snapp.phonebook.exceptions.ServiceException;
import com.snapp.phonebook.exceptions.error.ErrorCode;

import java.util.logging.Logger;

public class ErrorUtils {
    private final Logger logger = Logger.getLogger("ErrorUtils");

    public ServiceException extractServiceException(Throwable throwable) {

        String message = "";

        if (throwable != null && throwable.getCause() != null)
            message = throwable.getCause().getMessage();

        logger.info("Exception body is " + message);

        int indexOf = message.lastIndexOf("content:");

        try {
            String content = message.substring(indexOf + 8);
            int codeIndex = content.lastIndexOf("errorCode");
            int dataIndex = content.lastIndexOf("data");
            int errorCode = Integer.parseInt(content.substring(codeIndex + 11, content.length() - 1));
            String data = content.substring(dataIndex + 7, content.lastIndexOf(',') - 1);
            return new ServiceException(data, errorCode);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isSameErrorCode(ErrorCode errorCode, Throwable throwable) {
        ServiceException serviceException = extractServiceException(throwable);
        return serviceException != null && errorCode.getCode() == serviceException.errorCode;
    }

}
