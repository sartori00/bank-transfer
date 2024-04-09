package br.com.itau.banktransfer.client.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AccountResponseDto(
        @NotBlank String id,
        @NotNull BigDecimal saldo,
        @NotNull Boolean ativo,
        @PositiveOrZero @NotNull BigDecimal limiteDiario
) {
    public AccountResponseDto(String id, Boolean ativo) {
        this(id, null, ativo, null);
    }
}
