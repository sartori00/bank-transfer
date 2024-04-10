package br.com.itau.banktransfer.exception.handler;

import br.com.itau.banktransfer.exception.BusinessException;
import br.com.itau.banktransfer.exception.FallbackException;
import br.com.itau.banktransfer.exception.InvalidStatusException;
import br.com.itau.banktransfer.exception.RateLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        var errors = ex.getFieldErrors();
        log.error("[ApiExceptionHandler] - MethodArgumentNotValid -> {}", errors);
        return ResponseEntity.badRequest().body(errors.stream().map(ErrorsValidateData::new).toList());
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = ProblemDto.builder().message(ex.getMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - HttpMessageNotReadable -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle500Error(Exception ex) {
        var error = ProblemDto.builder().message("Error: " + ex.getLocalizedMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - internalServerError -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessError(BusinessException ex){
        var error = ProblemDto.builder().message(ex.getMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - BusinessError -> {}", error);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(FallbackException.class)
    public ResponseEntity<?> handleFallbackException(FallbackException ex){
        var error = ProblemDto.builder().message(ex.getMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - FallbackException -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<?> handleInvalidStatusException(InvalidStatusException ex){
        var error = ProblemDto.builder().message(ex.getMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - InvalidStatusException -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<?> handleRateLimitException(RateLimitException ex){
        var error = ProblemDto.builder().message(ex.getMessage()).dateTime(OffsetDateTime.now()).build();
        log.error("[ApiExceptionHandler] - RateLimitException -> {}", error);
        return ResponseEntity.internalServerError().body(error);
    }
}
