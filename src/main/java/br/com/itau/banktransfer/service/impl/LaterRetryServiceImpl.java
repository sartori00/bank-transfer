package br.com.itau.banktransfer.service.impl;

import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;
import br.com.itau.banktransfer.infrastructure.entity.enums.RetryStatus;
import br.com.itau.banktransfer.infrastructure.repository.LaterRetryRepository;
import br.com.itau.banktransfer.service.LaterRetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LaterRetryServiceImpl implements LaterRetryService {

    private final LaterRetryRepository repository;

    @Autowired
    public LaterRetryServiceImpl(LaterRetryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(LaterRetry laterRetry) {
        repository.save(laterRetry);

        log.info("Save transaction {} for future retry", laterRetry.getIdTransfer());
    }

    @Override
    public List<LaterRetry> findPending(){
        return repository.findByRetryStatus(RetryStatus.WAITING);
    }

    @Override
    public void deleteAllOfThis(List<LaterRetry> pendingList) {
        pendingList.forEach(repository::delete);

        log.info("Deleted all processed items");
    }
}
