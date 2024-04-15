package br.com.itau.banktransfer.service.impl;

import br.com.itau.banktransfer.client.account.AccountClient;
import br.com.itau.banktransfer.client.account.dto.SubmitAccountDto;
import br.com.itau.banktransfer.exception.FallbackException;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.service.SubmitTransactionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SubmitTransactionServiceImpl implements SubmitTransactionService {

    private final AccountClient client;

    @Autowired
    public SubmitTransactionServiceImpl(AccountClient client) {
        this.client = client;
    }

    @Override
    @CircuitBreaker(name = "transaction", fallbackMethod = "getTransactionFallback")
    public void submitTransaction(Transaction transaction){
        client.submitTransaction(new SubmitAccountDto(transaction));

        log.info("Transaction was submitted successfully - {}", transaction.getIdTransfer());
    }

    @Override
    public void getTransactionFallback(Transaction transaction, Throwable cause){
        log.error("Fallback Transaction with id {}", transaction.getIdTransfer());

        throw new FallbackException(cause);
    }
}
