package br.com.itau.banktransfer.event.subscriber;

import br.com.itau.banktransfer.event.NewTransactionSavedEvent;
import br.com.itau.banktransfer.service.BacenNotificationService;
import br.com.itau.banktransfer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class NotifyBacenSubscriber implements ApplicationListener<NewTransactionSavedEvent> {

    private final TransactionService transactionService;
    private final BacenNotificationService bacenNotificationService;

    @Override
    public void onApplicationEvent(NewTransactionSavedEvent event) {
        log.info("Subscriber NotifyBacen heard TransactionSavedEvent {}", event.getTransaction().getIdTransfer());
        var transaction = transactionService.findByIdTransfer(event.getTransaction().getIdTransfer());

        bacenNotificationService.notify(transaction);

        transaction.setSentToBacen();
        transactionService.save(transaction);
    }
}
