package br.com.itau.banktransfer.event.subscriber;

import br.com.itau.banktransfer.event.NewTransactionSavedEvent;
import br.com.itau.banktransfer.service.BacenNotificationService;
import br.com.itau.banktransfer.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2)
public class NotifyBacenSubscriber implements ApplicationListener<NewTransactionSavedEvent> {

    private final TransactionService transactionService;
    private final BacenNotificationService bacenNotificationService;

    @Autowired
    public NotifyBacenSubscriber(TransactionService transactionService, BacenNotificationService bacenNotificationService) {
        this.transactionService = transactionService;
        this.bacenNotificationService = bacenNotificationService;
    }


    @Override
    public void onApplicationEvent(NewTransactionSavedEvent event) {
        log.info("[NotifyBacenSubscriber] Subscriber NotifyBacen heard TransactionSavedEvent {}", event.getTransaction().getIdTransfer());

        var transaction = transactionService.findByIdTransfer(event.getTransaction().getIdTransfer());

        bacenNotificationService.notify(transaction);

        transaction.setSentToBacen();
        transactionService.save(transaction);
    }
}
