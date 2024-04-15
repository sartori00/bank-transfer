package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;

import java.util.List;

public interface LaterRetryService {
    void save(LaterRetry laterRetry);

    List<LaterRetry> findPending();

    void deleteAllOfThis(List<LaterRetry> pendingList);
}
