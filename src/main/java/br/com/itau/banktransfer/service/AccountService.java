package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.client.account.dto.AccountResponseDto;

public interface AccountService {
    AccountResponseDto getAccount(String accountId);

    AccountResponseDto getAccountFallback(String accountId, Throwable cause);
}
