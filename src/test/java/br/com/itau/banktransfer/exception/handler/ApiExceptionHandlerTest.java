package br.com.itau.banktransfer.exception.handler;

import br.com.itau.banktransfer.exception.BusinessException;
import br.com.itau.banktransfer.exception.FallbackException;
import br.com.itau.banktransfer.exception.InvalidStatusException;
import br.com.itau.banktransfer.exception.RateLimitException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiExceptionHandlerTest {

    @Mock
    private WebRequest mockWebRequest;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;


    @Test
    void handleMethodArgumentNotValid() {
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);
        HttpHeaders headers = new HttpHeaders();
        HttpStatusCode status = HttpStatus.BAD_REQUEST;


        assertEquals(apiExceptionHandler.handleMethodArgumentNotValid(exception, headers, status, mockWebRequest).getStatusCode(), status);

    }

    @Test
    void handleMethodArgumentNotValidWithFieldErrors() {
        FieldError fieldError = new FieldError("objectName", "fieldName", "error message");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);
        HttpHeaders headers = new HttpHeaders();
        HttpStatusCode status = HttpStatus.BAD_REQUEST;

        assertEquals(apiExceptionHandler.handleMethodArgumentNotValid(exception, headers, status, mockWebRequest).getStatusCode(), status);
    }

    @Test
    void handleHttpMessageNotReadable() {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("");
        HttpHeaders headers = new HttpHeaders();
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;

        assertEquals(apiExceptionHandler.handleHttpMessageNotReadable(exception, headers, status, mockWebRequest).getStatusCode(), status);
    }

    @Test
    void handle500Error() {
        Exception exception = new Exception();

        assertEquals(apiExceptionHandler.handle500Error(exception).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleBusinessError() {
        BusinessException exception = new BusinessException("");

        assertEquals(apiExceptionHandler.handleBusinessError(exception).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleFallbackException() {
        FallbackException exception = new FallbackException(new Throwable());

        assertEquals(apiExceptionHandler.handleFallbackException(exception).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleInvalidStatusException() {
        InvalidStatusException exception = new InvalidStatusException("");

        assertEquals(apiExceptionHandler.handleInvalidStatusException(exception).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleRateLimitException() {
        RateLimitException exception = new RateLimitException("");

        assertEquals(apiExceptionHandler.handleRateLimitException(exception).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
