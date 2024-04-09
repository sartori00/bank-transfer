package br.com.itau.banktransfer.exception;

public class FallbackException extends RuntimeException {
    public FallbackException(Throwable cause){
        super("We are unavailable right now, please try again later.", cause);
    }
}
