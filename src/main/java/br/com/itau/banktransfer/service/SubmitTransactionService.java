package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;

public interface SubmitTransactionService {
    void submitTransaction(Transaction transaction);

    void getTransactionFallback(Transaction transaction, Throwable cause);
}
