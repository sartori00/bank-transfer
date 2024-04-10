package br.com.itau.banktransfer.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message){
        super(message);
    }
}
