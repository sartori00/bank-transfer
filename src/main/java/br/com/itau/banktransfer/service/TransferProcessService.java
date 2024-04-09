package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class TransferProcessService {

    public TransferResponseDto processTransfer(TransferRequestDto dto) {
        UUID idTransfer = UUID.randomUUID();

        log.info("[TransferProcessService] Started process transfer  {}", idTransfer);

        return new TransferResponseDto(idTransfer);
    }
}
