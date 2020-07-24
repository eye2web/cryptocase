package com.eye2web.crypto.controller.advice;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalControllerAdvice {

    @ExceptionHandler({NotFoundException.class, ConversionFailedException.class, MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Void> handleNotFoundException(final Exception ex, final WebRequest webRequest) {

        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
