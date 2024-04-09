package br.com.itau.banktransfer.validation;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.client.account.dto.AccountResponseDto;
import br.com.itau.banktransfer.client.customer.dto.CustomerResponseDto;

import java.util.UUID;

public record ItemsForValidation(
        CustomerResponseDto destinationCustomer,
        AccountResponseDto originAccount,
        TransferRequestDto dto,
        UUID idTransfer
) {

}
