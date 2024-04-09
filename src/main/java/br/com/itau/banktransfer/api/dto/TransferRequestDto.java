package br.com.itau.banktransfer.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

public record TransferRequestDto(
        @JsonProperty("idCliente")
        @UUID @NotNull
        @Schema(example = "bcdd1048-a501-4608-bc82-66d7b4db3600")
        String destinationCustomerId,

        @JsonProperty("valor")
        @Positive @NotNull
        @Schema(example = "500")
        BigDecimal amount,

        @JsonProperty("conta")
        @Valid @NotNull
        AccountRequestDto account
) {
}
