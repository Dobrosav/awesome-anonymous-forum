package com.thereputeo.awesomeanonymousforum.exception;

import com.thereputeo.awesomeanonymousforum.model.ApiErrorResponse;
import com.thereputeo.awesomeanonymousforum.model.ApiResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.StringJoiner;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiGlobalErrorHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ApiGlobalErrorHandler.class.getName());

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    protected ResponseEntity handleServiceException(ServiceException ex) {
        LOG.warn("ServiceException occurred:{},code-{}", ex.getErrorType(), ex.getErrorType().getCode());
        return new ResponseEntity(new ApiResponseWrapper<>(new ApiErrorResponse(ex.getHttpCode().value() + "." + "001" + "." + ex.getErrorType().getCode(), ex.getErrorType().getMessage())), ex.getHttpCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected ApiResponseWrapper handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ApiResponseWrapper<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value() + "." + "001" + "." + ErrorType.UNRESOLVED_ERROR.getCode(), ErrorType.UNRESOLVED_ERROR.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ApiResponseWrapper handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ApiResponseWrapper<>(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value() + "." + "001" + "." + ErrorType.NOT_SUPPORTED_HTTP_METHOD.getCode(), ErrorType.NOT_SUPPORTED_HTTP_METHOD.getMessage() + ":" + ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ApiResponseWrapper handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ApiResponseWrapper(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value() + "." + "001" + "." + ErrorType.NOT_VALID_REQUEST_FORMAT.getCode(), ErrorType.NOT_VALID_REQUEST_FORMAT.getMessage() + ":" + ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ApiResponseWrapper handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        LOG.warn("Bad request occured,fields:{} not valid", ex.getBindingResult().getFieldErrors());
        List<FieldError> bindingResult = ex.getBindingResult().getFieldErrors();
        StringJoiner errorFields = new StringJoiner(",");
        for (FieldError error : bindingResult) {
            errorFields.add(error.getField());
        }
        return new ApiResponseWrapper<>(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value() + "." + "001" + "." + ErrorType.INVALID_ARGUMENT.getCode(), ErrorType.INVALID_ARGUMENT.getMessage() + ":" + errorFields));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected ApiResponseWrapper handleRuntimeException(RuntimeException ex) {
        LOG.error("Occurred unexpected error", ex);
        return new ApiResponseWrapper<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value() + "." + "001" + "." + ErrorType.UNRESOLVED_ERROR.getCode(), ErrorType.UNRESOLVED_ERROR.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected ApiResponseWrapper handleException(Exception ex) {
        LOG.error("Occurred unexpected error", ex);
        return new ApiResponseWrapper<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value() + "." + "001 " + "." + ErrorType.UNRESOLVED_ERROR.getCode(), ErrorType.UNRESOLVED_ERROR.getMessage()));
    }
}
