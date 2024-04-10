package br.com.itau.banktransfer.infrastructure.repository;

import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;
import br.com.itau.banktransfer.infrastructure.entity.enums.RetryStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaterRetryRepository extends MongoRepository<LaterRetry, String> {

    List<LaterRetry> findByRetryStatus(RetryStatus retryStatus);
}
