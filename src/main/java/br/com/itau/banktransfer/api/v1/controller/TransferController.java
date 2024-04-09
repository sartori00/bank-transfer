package br.com.itau.banktransfer.api.v1.controller;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import br.com.itau.banktransfer.service.TransferProcessService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfer")
public class TransferController{

    private final TransferProcessService service;

    @Autowired
    public TransferController(TransferProcessService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransferResponseDto> doTransfer(@RequestBody @Valid TransferRequestDto dto){
        return ResponseEntity.ok(service.processTransfer(dto));
    }
}