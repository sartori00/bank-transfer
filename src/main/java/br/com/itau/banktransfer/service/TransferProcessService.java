package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransferProcessService {

    private final CustomerService customerService;
    private final AccountService accountService;

    @Autowired
    public TransferProcessService(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    public TransferResponseDto processTransfer(TransferRequestDto dto) {
        UUID idTransfer = UUID.randomUUID();

        log.info("[TransferProcessService] Started process transfer  {}", idTransfer);

        var destinationCustomer = customerService.getCustomer(dto.destinationCustomerId());
        var originAccount = accountService.getAccount(dto.account().originAccount());

        return new TransferResponseDto(idTransfer);
    }


}
