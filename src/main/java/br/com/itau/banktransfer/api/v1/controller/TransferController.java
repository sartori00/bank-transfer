package br.com.itau.banktransfer.api.v1.controller;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import br.com.itau.banktransfer.api.v1.openapi.TransferControllerOpenApi;
import br.com.itau.banktransfer.service.TransferProcessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class TransferController implements TransferControllerOpenApi {

    private final TransferProcessService service;

    @PostMapping
    public ResponseEntity<TransferResponseDto> doTransfer(@RequestBody @Valid TransferRequestDto dto){
        return ResponseEntity.ok(service.processTransfer(dto));
    }
}