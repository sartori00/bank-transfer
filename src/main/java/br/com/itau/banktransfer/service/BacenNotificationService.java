package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.client.bacen.BacenClient;
import br.com.itau.banktransfer.client.bacen.dto.BacenNotificationDto;
import br.com.itau.banktransfer.exception.FallbackException;
import br.com.itau.banktransfer.exception.InvalidStatusException;
import br.com.itau.banktransfer.exception.RateLimitException;
import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.infrastructure.entity.enums.TransactionStatus;
import br.com.itau.banktransfer.util.ResponseMessages;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BacenNotificationService {

    private final BacenClient bacenClient;
    private final LaterRetryService laterRetryService;

    @Autowired
    public BacenNotificationService(BacenClient bacenClient, LaterRetryService laterRetryService) {
        this.bacenClient = bacenClient;
        this.laterRetryService = laterRetryService;
    }

    @CircuitBreaker(name = "notify", fallbackMethod = "getNotifyFallback")
    public void notify(Transaction transaction){
        log.info("[BacenNotificationService] Started notification to Bacen {}", transaction.getIdTransfer());

        if(!transaction.getStatus().equals(TransactionStatus.TRANSFERRED)) {
            throw new InvalidStatusException(ResponseMessages.TRANSACTION_IS_NOT_COMPLETED);
        }

        bacenClient.notifyTransaction(new BacenNotificationDto(transaction));
    }

    public void getNotifyFallback(Transaction transaction, Throwable cause){
        laterRetryService.save(new LaterRetry(transaction.getIdTransfer()));

        if(cause instanceof FeignException.TooManyRequests) {
            log.error("[BacenNotificationService] Bacen API blocked this request due to Rate Limit, id: {}", transaction.getIdTransfer());

            throw new RateLimitException(ResponseMessages.RATE_LIMIT_BLOCKED);
        }
        log.error("[BacenNotificationService] fallback Transaction with id {}", transaction.getIdTransfer());

        throw new FallbackException(cause);
    }
}
