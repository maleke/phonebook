package com.snapp.phonebook.exceptions;


import com.snapp.phonebook.common.CommonResponseDto;
import com.snapp.phonebook.dto.MessageDTO;
import com.snapp.phonebook.enums.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Locale;


@ControllerAdvice
public class ControllerValidationHandler {

    @Autowired
    private MessageSource msgSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();

        return processFieldError(error);
    }

    private MessageDTO processFieldError(FieldError error) {
        MessageDTO message = null;
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            message = new MessageDTO(MessageType.ERROR, msg);
        }
        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<CommonResponseDto> exception(Exception exception, WebRequest request) {

        CommonResponseDto response = new CommonResponseDto();
        response.setResult(CommonResponseDto.RESULT.FAILURE);
//        response.setData(exception.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        exception.printStackTrace();

        if (exception instanceof HttpMessageNotReadableException) {
            response.setData(exception.getMessage());
            response.setErrorCode(ErrorCodes.BAD_INPUT.getCode());

        } else if (exception instanceof ServiceException) {
            int ErrorCode = ((ServiceException) exception).getErrorCode();

            response.setData(exception.getMessage());
            response.setErrorCode(ErrorCode);


            if (ErrorCode == ErrorCodes.ACCESS_DENIED.getCode()
                    || ErrorCode == ErrorCodes.INACTIVE_USER.getCode()
                    || ErrorCode == ErrorCodes.NO_USER.getCode()) {

                status = HttpStatus.FORBIDDEN;

            } else if (ErrorCode == ErrorCodes.NO_ENTITY.getCode()) {
                status = HttpStatus.NOT_FOUND;
            }

        } else if (exception instanceof AccessDeniedException) {
            response.setData("Access denied");
            response.setErrorCode(ErrorCodes.ACCESS_DENIED.getCode());
            status = HttpStatus.FORBIDDEN;

        } else if (exception instanceof org.springframework.dao.DataIntegrityViolationException) {
            response.setData("input data integrity is not correct.");
            response.setErrorCode(ErrorCodes.BAD_INPUT.getCode());
            status = HttpStatus.BAD_REQUEST;

        } else if (exception instanceof RuntimeException) {
            response.setData("Internal error");
            response.setErrorCode(ErrorCodes.INTERNAL_ERROR.getCode());
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }

        return new ResponseEntity<>(response, status);
    }
}
