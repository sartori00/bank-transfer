package br.com.itau.banktransfer.api.v1.openapi;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Transfer")
public interface TransferControllerOpenApi {

    @Operation(summary = "Make a transfer from one account to another")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(ref = "TransferResponseDto")))
    @ApiResponse(responseCode = "400", description = "BadRequestResponse", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(ref = "ProblemDto")),
            @Content(mediaType = "application/json",
                    schema = @Schema(ref = "ErrorsValidateData"))
    })
    @ApiResponse(responseCode = "500", description = "InternalServerErrorResponse", content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
    ResponseEntity<TransferResponseDto> doTransfer(
            @RequestBody(description = "Representation of a transfer", required = true) TransferRequestDto dto);
}
