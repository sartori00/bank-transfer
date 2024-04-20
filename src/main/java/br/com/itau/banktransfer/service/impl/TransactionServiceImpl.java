package br.com.itau.banktransfer.service.impl;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.infrastructure.repository.TransactionRepository;
import br.com.itau.banktransfer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    @Override
    public Transaction save(Transaction transaction){
        var transactionSaved = repository.save(transaction);
        log.info("Transaction Saved on database {}", transactionSaved.getIdTransfer());

        return transactionSaved;
    }

    @Override
    public Transaction findByIdTransfer(String idTransfer) {
        return repository.findByIdTransfer(idTransfer)
                .orElseThrow();
    }
}
