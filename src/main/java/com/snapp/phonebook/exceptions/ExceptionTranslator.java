package com.snapp.phonebook.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.snapp.phonebook.exceptions.error.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class ExceptionTranslator {

    private MessageSource messageSource;

    @Autowired
    public ExceptionTranslator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<FieldErrorDTO> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(CustomParameterizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ParameterizedErrorDTO processParameterizedValidationError(CustomParameterizedException ex) {
        return ex.getErrorDTO();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public FieldErrorDTO processHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        StringBuilder field = new StringBuilder();
        Throwable throwable = e.getCause();
        JsonMappingException jsonMappingException = ((JsonMappingException) throwable);
        List<JsonMappingException.Reference> references = jsonMappingException.getPath();
        for (JsonMappingException.Reference reference : references) {
            if (reference.getFieldName() != null) {
                field.append(reference.getFieldName()).append(":");
            }
        }
        String message = jsonMappingException.getOriginalMessage();
        return new FieldErrorDTO()
                .setErrorCode(String.valueOf(ErrorCode.DATA_MISMATCH_WITH_PROTOCOL.getCode()))
                .setErrorDescription(ErrorCode.DATA_MISMATCH_WITH_PROTOCOL.getMessage())
                .setReferenceName(field.substring(0, field.length() - 1))
                .setExtraData(message);
    }

    private List<FieldErrorDTO> processFieldErrors(List<FieldError> fieldErrors) {
        List<FieldErrorDTO> fieldErrorDTOS = new ArrayList<>();
        FieldErrorDTO fieldErrorDTO;
        for (FieldError fieldError : fieldErrors) {
            //todo:: Locale currentLocale = LocaleContextHolder.getLocale();
            fieldErrorDTO = new FieldErrorDTO();
            fieldErrorDTO
                    .setExtraData("objectName is: " + fieldError.getObjectName() + " description is: " + fieldError.getDefaultMessage())
                    .setOriginalValue(String.valueOf(fieldError.getRejectedValue()))
                    .setReferenceName(fieldError.getField());
            switch (fieldError.getCode()) {
                case "NotNull":
                    fieldErrorDTO
                            .setErrorCode(String.valueOf(ErrorCode.REQUIRED_FIELD_NULL_VALUE.getCode()))
                            .setErrorDescription(ErrorCode.REQUIRED_FIELD_NULL_VALUE.getMessage());
                    break;

                default:
                    fieldErrorDTO
                            .setErrorCode(String.valueOf(ErrorCode.DATA_MISMATCH_WITH_PROTOCOL.getCode()))
                            .setErrorDescription(ErrorCode.DATA_MISMATCH_WITH_PROTOCOL.getMessage());
                    break;
            }
            for (FieldErrorDTO oldFieldErrorDTO : fieldErrorDTOS) {
                if (fieldErrorDTO.getErrorCode().equals(oldFieldErrorDTO.getErrorCode())) {
                    String oldExtraData = oldFieldErrorDTO.getExtraData();
                    String oldDefaultMessage = oldExtraData.substring(oldExtraData.lastIndexOf("is") + 3, oldExtraData.length());
                    fieldErrorDTO.setReferenceName(fieldErrorDTO.getReferenceName() + ":" + oldFieldErrorDTO.getReferenceName())
                            .setOriginalValue(fieldErrorDTO.getOriginalValue() + ":" + oldFieldErrorDTO.getOriginalValue())
                            .setExtraData(fieldErrorDTO.getExtraData() + "\\n," + oldDefaultMessage);
                    fieldErrorDTOS.remove(oldFieldErrorDTO);
                    if(fieldErrorDTOS.size() == 0)
                        break;
                }
            }
            fieldErrorDTOS.add(fieldErrorDTO);
        }
        return fieldErrorDTOS;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDTO processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorDTO(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, exception.getMessage());
    }

    @ExceptionHandler(StringIndexOutOfBoundsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public FieldErrorDTO processStringIndexOutOfBoundsException(StringIndexOutOfBoundsException exception) {
        return new FieldErrorDTO()
                .setErrorCode(String.valueOf(ErrorCode.INTERNAL_ERROR.getCode()))
                .setErrorDescription(ErrorCode.INTERNAL_ERROR.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public FieldErrorDTO processServiceException(ServiceException se) {
        return se.getFieldErrorDTO();
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
