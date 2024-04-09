package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.infrastructure.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction save(Transaction transaction){
        var transactionSaved = repository.save(transaction);
        log.info("[TransactionService] Transaction Saved on database {}", transactionSaved.getIdTransfer());

        return transactionSaved;
    }

}
