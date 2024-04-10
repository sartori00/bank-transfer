package br.com.itau.banktransfer.event.subscriber;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.event.NewTransactionSavedEvent;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.infrastructure.entity.enums.TransactionStatus;
import br.com.itau.banktransfer.service.BacenNotificationService;
import br.com.itau.banktransfer.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotifyBacenSubscriberTest {

    @Mock
    TransactionService transactionService;

    @Mock
    BacenNotificationService bacenNotificationService;

    @InjectMocks
    NotifyBacenSubscriber notifyBacenSubscriber;

    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;

    Transaction transactionToNotify;

    @BeforeEach
    void setUp() {
        this.buildTransaction();
    }

    @Test
    void shouldNotifyBacenAndUpdateStatusAndTimeOnDataBase() {
        when(transactionService.findByIdTransfer(anyString())).thenReturn(transactionToNotify);

        notifyBacenSubscriber.onApplicationEvent(new NewTransactionSavedEvent(this, transactionToNotify));


        verify(bacenNotificationService, times(ConstantTimes.ONLY_ONCE)).notify(transactionToNotify);
        verify(transactionService, times(ConstantTimes.ONLY_ONCE)).save(transactionCaptor.capture());

        var transactionSaved = transactionCaptor.getValue();

        assertEquals(transactionSaved.getStatus(), TransactionStatus.SENT_TO_BACEN);
        assertNotNull(transactionSaved.getProcessedAt());
        assertNotNull(transactionSaved.getTransferredAt());
        assertNotNull(transactionSaved.getSentToBacenAt());
    }

    @Test
    void shouldNotUpdateStatusWhenAnExceptionHappenedOnBacenServices() {
        when(transactionService.findByIdTransfer(anyString())).thenReturn(transactionToNotify);
        doThrow(new RuntimeException()).when(bacenNotificationService).notify(transactionToNotify);

        assertThrows(RuntimeException.class, () ->
                notifyBacenSubscriber.onApplicationEvent(new NewTransactionSavedEvent(this, transactionToNotify))
        );

        assertEquals(transactionToNotify.getStatus(), TransactionStatus.TRANSFERRED);
        assertNull(transactionToNotify.getSentToBacenAt());
        verify(bacenNotificationService, times(ConstantTimes.ONLY_ONCE)).notify(transactionToNotify);
        verifyNoMoreInteractions(transactionService);
    }


    private void buildTransaction() {
        transactionToNotify = new Transaction.TransactionBuilder()
                .destinationCustomerId("bcdd1048-a501-4608-bc82-66d7b4db3600")
                .amount(BigDecimal.TEN)
                .originAccount("d0d32142-74b7-4aca-9c68-838aeacef96b")
                .toAccount("41313d7b-bd75-4c75-9dea-1f4be434007f")
                .idTransfer(UUID.randomUUID().toString())
                .build();

        transactionToNotify.setTransferred();
    }

}