package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;

public interface TransactionService {
    Transaction save(Transaction transaction);

    Transaction findByIdTransfer(String idTransfer);
}
