package br.com.itau.banktransfer.api.v1.controller;


import br.com.itau.banktransfer.schedule.LaterRetrySchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/retry")
public class RetryController {

    private final LaterRetrySchedule service;

    @Autowired
    public RetryController(LaterRetrySchedule service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> retryScheduled(){
        service.retryScheduled();

        return ResponseEntity.ok().build();
    }
}
