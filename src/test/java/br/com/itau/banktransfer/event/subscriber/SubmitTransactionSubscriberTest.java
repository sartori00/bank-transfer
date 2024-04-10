package br.com.itau.banktransfer.event.subscriber;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.event.NewTransactionSavedEvent;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.infrastructure.entity.enums.TransactionStatus;
import br.com.itau.banktransfer.service.SubmitTransactionService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubmitTransactionSubscriberTest {

    @Mock
    private SubmitTransactionService submitService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private SubmitTransactionSubscriber submitTransactionSubscriber;

    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;

    Transaction transactionToSubmit;

    @BeforeEach
    void setUp() {
        this.buildTransaction();
    }

    @Test
    void shouldSubmitTransaction() {
        submitTransactionSubscriber.onApplicationEvent(new NewTransactionSavedEvent(this, transactionToSubmit));

        verify(submitService, times(ConstantTimes.ONLY_ONCE)).submitTransaction(transactionToSubmit);
        verify(transactionService, times(ConstantTimes.ONLY_ONCE)).save(transactionCaptor.capture());

        var transactionSaved = transactionCaptor.getValue();

        assertEquals(transactionSaved.getStatus(), TransactionStatus.TRANSFERRED);
        assertNotNull(transactionSaved.getProcessedAt());
        assertNotNull(transactionSaved.getTransferredAt());
        assertNull(transactionSaved.getSentToBacenAt());
    }

    private void buildTransaction() {
        transactionToSubmit = new Transaction.TransactionBuilder()
                .destinationCustomerId("bcdd1048-a501-4608-bc82-66d7b4db3600")
                .amount(BigDecimal.TEN)
                .originAccount("d0d32142-74b7-4aca-9c68-838aeacef96b")
                .toAccount("41313d7b-bd75-4c75-9dea-1f4be434007f")
                .idTransfer(UUID.randomUUID().toString())
                .build();
    }
}
