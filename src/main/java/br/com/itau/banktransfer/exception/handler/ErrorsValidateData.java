package br.com.itau.banktransfer.exception.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.FieldError;

@Schema(name = "ErrorsValidateData")
public record ErrorsValidateData(
        @Schema(example = "idCliente")
        String field,

        @Schema(example = "must be a valid UUID")
        String message
){
    public ErrorsValidateData(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}