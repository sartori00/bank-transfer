package br.com.itau.banktransfer.exception;

public class RateLimitException extends RuntimeException {
    public RateLimitException(String message){
        super(message);
    }
}
