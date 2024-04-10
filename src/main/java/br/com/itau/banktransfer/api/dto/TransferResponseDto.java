package br.com.itau.banktransfer.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record TransferResponseDto(
        @JsonProperty("idTransferencia")
        @Schema(example = "ab69e046-fb5a-4a79-98d6-363efdf20e11")
        UUID idTransfer
) {
}
