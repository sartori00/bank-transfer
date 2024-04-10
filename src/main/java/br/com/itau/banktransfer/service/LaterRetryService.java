package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;
import br.com.itau.banktransfer.infrastructure.entity.enums.RetryStatus;
import br.com.itau.banktransfer.infrastructure.repository.LaterRetryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LaterRetryService {

    private final LaterRetryRepository repository;

    @Autowired
    public LaterRetryService(LaterRetryRepository repository) {
        this.repository = repository;
    }

    public void save(LaterRetry laterRetry) {
        repository.save(laterRetry);

        log.info("[LaterRetryService] - Save transaction {} for future retry", laterRetry.getIdTransfer());
    }

    public List<LaterRetry> findPending(){
        return repository.findByRetryStatus(RetryStatus.WAITING);
    }

    public void deleteAllOfThis(List<LaterRetry> pendingList) {
        pendingList.forEach(repository::delete);

        log.info("[LaterRetryService] - Deleted all processed items");
    }
}
