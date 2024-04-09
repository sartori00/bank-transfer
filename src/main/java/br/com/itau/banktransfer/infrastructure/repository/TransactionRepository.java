package br.com.itau.banktransfer.infrastructure.repository;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
