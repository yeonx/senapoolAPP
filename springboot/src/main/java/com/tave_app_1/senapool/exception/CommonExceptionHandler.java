package com.tave_app_1.senapool.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ErrorResponse<?> processValidationError(BindException exception) {
        log.error("BindingException 발생", exception);
        return new ErrorResponse<>(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ErrorResponse<?> processUrlError(FileNotFoundException exception) {
        log.error("FileNotFoundException 발생", exception);
        return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ErrorResponse<?> processFileError(IOException exception) {
        log.error("IOException 발생", exception);
        return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ErrorResponse<?> processNotFoundError(CustomException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse<>(ErrorCode.INVALID_REQUEST);
    }
}
