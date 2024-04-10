package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.client.bacen.BacenClient;
import br.com.itau.banktransfer.client.bacen.dto.BacenNotificationDto;
import br.com.itau.banktransfer.exception.FallbackException;
import br.com.itau.banktransfer.exception.InvalidStatusException;
import br.com.itau.banktransfer.exception.RateLimitException;
import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.infrastructure.entity.enums.RetryStatus;
import feign.FeignException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BacenNotificationServiceTest {

    @Mock
    private BacenClient bacenClient;

    @Mock
    private LaterRetryService laterRetryService;

    @InjectMocks
    private BacenNotificationService bacenNotificationService;

    @Captor
    ArgumentCaptor<LaterRetry> laterRetryCaptor;

    private Transaction validTransaction;
    private Transaction invalidTransaction;

    @BeforeEach
    void setUp() {
        this.buildTransactions();
    }

    @Test
    void shouldNotifyBacenAndDoesNotThrowsException() {
        assertDoesNotThrow(() -> {
            bacenNotificationService.notify(validTransaction);
        });
        verify(bacenClient, times(ConstantTimes.ONLY_ONCE)).notifyTransaction(any(BacenNotificationDto.class));
    }

    @Test
    void shouldThrowsInvalidStatusExceptionWhenTransactionInStatusDifferentOfTransferred() {
        assertThrows(InvalidStatusException.class, () -> {
            bacenNotificationService.notify(invalidTransaction);
        });
        verifyNoInteractions(bacenClient);
        verifyNoInteractions(laterRetryService);
    }

    @Test
    void shouldThrowsRateLimitExceptionWhenBacensResponseIsTooManyRequests() {
        FeignException.TooManyRequests feignException = mock(FeignException.TooManyRequests.class);
        assertThrows(RateLimitException.class, () -> {
            bacenNotificationService.getNotifyFallback(validTransaction, feignException);
        });
        verify(laterRetryService, times(ConstantTimes.ONLY_ONCE)).save(laterRetryCaptor.capture());

        var laterRetrySaved = laterRetryCaptor.getValue();

        assertNotNull(laterRetrySaved.getIdTransfer());
        assertNotNull(laterRetrySaved.getCreatedAt());
        assertEquals(laterRetrySaved.getRetryStatus(), RetryStatus.WAITING);

        verifyNoInteractions(bacenClient);
    }

    @Test
    void shouldCallFallbackMethodWhenAnErrorIsThrownAndThrowsFallbackException() {
        Throwable cause = mock(Throwable.class);
        assertThrows(FallbackException.class, () -> {
            bacenNotificationService.getNotifyFallback(validTransaction, cause);
        });
        verify(laterRetryService, times(ConstantTimes.ONLY_ONCE)).save(any(LaterRetry.class));
        verifyNoInteractions(bacenClient);
    }

    private void buildTransactions() {
        validTransaction = new Transaction.TransactionBuilder()
                .destinationCustomerId("bcdd1048-a501-4608-bc82-66d7b4db3600")
                .amount(BigDecimal.TEN)
                .originAccount("d0d32142-74b7-4aca-9c68-838aeacef96b")
                .toAccount("41313d7b-bd75-4c75-9dea-1f4be434007f")
                .idTransfer(UUID.randomUUID().toString())
                .build();

        validTransaction.setTransferred();

        invalidTransaction = new Transaction.TransactionBuilder()
                .destinationCustomerId("bcdd1048-a501-4608-bc82-66d7b4db3600")
                .amount(BigDecimal.TEN)
                .originAccount("d0d32142-74b7-4aca-9c68-838aeacef96b")
                .toAccount("41313d7b-bd75-4c75-9dea-1f4be434007f")
                .idTransfer(UUID.randomUUID().toString())
                .build();
    }
}
