package br.com.itau.banktransfer.api.v1.controller;

import br.com.itau.banktransfer.api.v1.openapi.RetryControllerOpenApi;
import br.com.itau.banktransfer.schedule.LaterRetrySchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/retry")
@RequiredArgsConstructor
public class RetryController implements RetryControllerOpenApi {

    private final LaterRetrySchedule service;

    @GetMapping
    public ResponseEntity<?> retryScheduled(){
        service.retryScheduled();

        return ResponseEntity.noContent().build();
    }
}
