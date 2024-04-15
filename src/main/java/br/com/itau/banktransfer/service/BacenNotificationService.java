package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;

public interface BacenNotificationService {
    void notify(Transaction transaction);

    void getNotifyFallback(Transaction transaction, Throwable cause);
}
