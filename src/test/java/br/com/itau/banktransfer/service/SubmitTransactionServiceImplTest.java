package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.client.account.AccountClient;
import br.com.itau.banktransfer.exception.FallbackException;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.service.impl.SubmitTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitTransactionServiceImplTest {

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private SubmitTransactionServiceImpl submitTransactionService;

    private Transaction validTransaction;

    @BeforeEach
    void setUp() {
        this.buildTransactions();
    }

    @Test
    void shouldSubmitTransactionAndDoesNotThrowsException(){
        assertDoesNotThrow(() -> {
            submitTransactionService.submitTransaction(validTransaction);
        });
        verify(accountClient, times(ConstantTimes.ONLY_ONCE)).submitTransaction(any());
    }

    @Test
    void shouldCallFallbackMethodWhenAnErrorIsThrownAndThrowsFallbackException() {
        Throwable cause = mock(Throwable.class);
        assertThrows(FallbackException.class, () -> {
            submitTransactionService.getTransactionFallback(validTransaction, cause);
        });
        verifyNoInteractions(accountClient);
    }

    private void buildTransactions() {
        validTransaction = new Transaction.TransactionBuilder()
                .destinationCustomerId("customer123")
                .amount(BigDecimal.TEN)
                .originAccount("123456")
                .toAccount("654321")
                .idTransfer("transfer123")
                .build();

        validTransaction.setTransferred();
    }
}
