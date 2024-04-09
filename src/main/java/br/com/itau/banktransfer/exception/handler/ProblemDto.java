package br.com.itau.banktransfer.exception.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
@Schema(name = "ProblemDto")
public record ProblemDto(
        @Schema(example = "Error Message")
        String message,

        @Schema(example = "2024-04-08T13:35:57.681706Z")
        OffsetDateTime dateTime
){ }
