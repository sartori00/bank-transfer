package br.com.itau.banktransfer.schedule;

import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;
import br.com.itau.banktransfer.service.BacenNotificationService;
import br.com.itau.banktransfer.service.LaterRetryService;
import br.com.itau.banktransfer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LaterRetrySchedule {

    private final LaterRetryService laterRetryService;
    private final TransactionService transactionService;
    private final BacenNotificationService bacenNotificationService;

    @Scheduled(cron = "${schedule.later-retry-cron-pattern}")
    public void retryScheduled(){
        log.info("JOB - Started");
        List<LaterRetry> pendingList = laterRetryService.findPending();

        log.info("JOB - Found {} items to retry", pendingList.size());

        if(!pendingList.isEmpty()){
            pendingList.forEach(item -> this.retry(item.getIdTransfer()));

            laterRetryService.deleteAllOfThis(pendingList);
        }

        log.info("JOB - Finished");
    }

    private void retry(String idTransfer) {
        try {
            var transaction = transactionService.findByIdTransfer(idTransfer);
            bacenNotificationService.notify(transaction);
        } catch (NoSuchElementException e) {
            log.warn("JOB - idTransfer not found in transactions - {}", idTransfer);
        }
    }
}
