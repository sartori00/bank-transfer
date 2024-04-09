package br.com.itau.banktransfer.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;

public record AccountRequestDto(
        @JsonProperty("idOrigem")
        @UUID @NotBlank
        @Schema(example = "d0d32142-74b7-4aca-9c68-838aeacef96b")
        String originAccount,

        @JsonProperty("idDestino")
        @UUID @NotBlank
        @Schema(example = "41313d7b-bd75-4c75-9dea-1f4be434007f")
        String toAccount
) {
}
