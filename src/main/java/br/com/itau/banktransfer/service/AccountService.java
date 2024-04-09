package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.client.account.AccountClient;
import br.com.itau.banktransfer.client.account.dto.AccountResponseDto;
import br.com.itau.banktransfer.exception.FallbackException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountService {

    private final AccountClient accountClient;

    @Autowired
    public AccountService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @CircuitBreaker(name = "account", fallbackMethod = "getAccountFallback")
    public AccountResponseDto getAccount(String accountId){
        return accountClient.getAccount(accountId);
    }

    public AccountResponseDto getAccountFallback(String accountId, Throwable cause){
        if(cause instanceof FeignException.NotFound){
            return new AccountResponseDto(accountId, false);
        }

        log.error("[AccountService] fallback Account with accountId {}", accountId);
        throw new FallbackException(cause);
    }
}
