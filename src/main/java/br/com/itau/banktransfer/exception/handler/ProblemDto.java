package br.com.itau.banktransfer.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
@Schema(name = "ProblemDto")
public record ProblemDto(
        @Schema(example = "Error Message")
        String message,

        @Schema(example = "2024-04-09T22:13:58Z")
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
        OffsetDateTime dateTime
){ }
