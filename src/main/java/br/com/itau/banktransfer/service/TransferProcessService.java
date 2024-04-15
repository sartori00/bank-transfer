package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;

public interface TransferProcessService {
    TransferResponseDto processTransfer(TransferRequestDto dto);
}
