package com.eye2web.crypto.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class LastResortControllerAdvice {

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    public ResponseEntity<Void> handleException(final Exception ex, final WebRequest request) {

        log.error("Last Resort Error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
