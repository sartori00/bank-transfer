package br.com.itau.banktransfer.event.subscriber;

import br.com.itau.banktransfer.event.NewTransactionSavedEvent;
import br.com.itau.banktransfer.service.SubmitTransactionService;
import br.com.itau.banktransfer.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
public class SubmitTransactionSubscriber implements ApplicationListener<NewTransactionSavedEvent> {

    private final SubmitTransactionService submitService;
    private final TransactionService transactionService;

    @Autowired
    public SubmitTransactionSubscriber(SubmitTransactionService submitService, TransactionService transactionService) {
        this.submitService = submitService;
        this.transactionService = transactionService;
    }

    @Override
    public void onApplicationEvent(NewTransactionSavedEvent event) {
        log.info("Subscriber SubmitTransaction heard TransactionSavedEvent {}", event.getTransaction().getIdTransfer());
        var transaction = event.getTransaction();

        submitService.submitTransaction(transaction);

        transaction.setTransferred();
        transactionService.save(transaction);
    }
}
