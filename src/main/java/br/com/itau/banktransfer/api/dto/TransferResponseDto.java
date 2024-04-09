package br.com.itau.banktransfer.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record TransferResponseDto(
        @JsonProperty("idTransferencia") UUID idTransfer
) {
}
