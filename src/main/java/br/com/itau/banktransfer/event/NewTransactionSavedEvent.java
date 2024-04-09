package br.com.itau.banktransfer.event;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewTransactionSavedEvent extends ApplicationEvent {

    private final Transaction transaction;


    public NewTransactionSavedEvent(Object source, Transaction transaction) {
        super(source);
        this.transaction = transaction;
    }
}
