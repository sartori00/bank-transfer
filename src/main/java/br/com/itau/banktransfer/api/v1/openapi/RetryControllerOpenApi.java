package br.com.itau.banktransfer.api.v1.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Retry")
public interface RetryControllerOpenApi {

    @Operation(summary = "Manual activation of JOB attempts to send notification to BACEN")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "InternalServerErrorResponse", content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
    ResponseEntity<?> retryScheduled();
}
