package br.com.itau.banktransfer.schedule;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.service.BacenNotificationService;
import br.com.itau.banktransfer.service.LaterRetryService;
import br.com.itau.banktransfer.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaterRetryScheduleTest {

    @Mock
    private LaterRetryService laterRetryService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private BacenNotificationService bacenNotificationService;

    @InjectMocks
    private LaterRetrySchedule laterRetrySchedule;

    List<LaterRetry> pendingList;

    @BeforeEach
    void setUp() {
        this.buildArrange();
    }

    @Test
    void shouldRetryAllOfPendingFound() {
        when(laterRetryService.findPending()).thenReturn(pendingList);
        when(transactionService.findByIdTransfer(any())).thenReturn(new Transaction());
        doNothing().when(bacenNotificationService).notify(any());

        laterRetrySchedule.retryScheduled();

        verify(transactionService, times(ConstantTimes.THREE_TIMES)).findByIdTransfer(any());
        verify(bacenNotificationService, times(ConstantTimes.THREE_TIMES)).notify(any());
        verify(laterRetryService, times(ConstantTimes.ONLY_ONCE)).deleteAllOfThis(pendingList);
    }

    @Test
    void shouldDoesNothingWhenLaterRetriesNotFound(){
        when(laterRetryService.findPending()).thenReturn(new ArrayList<>());

        laterRetrySchedule.retryScheduled();

        verifyNoInteractions(transactionService);
        verifyNoInteractions(bacenNotificationService);
        verifyNoMoreInteractions(laterRetryService);
    }

    @Test
    void shouldIgnoreTransactionNotFound() {
        when(laterRetryService.findPending()).thenReturn(pendingList);
        when(transactionService.findByIdTransfer(any())).thenThrow(new NoSuchElementException());

        laterRetrySchedule.retryScheduled();

        verifyNoInteractions(bacenNotificationService);
        verify(laterRetryService, times(ConstantTimes.ONLY_ONCE)).deleteAllOfThis(pendingList);
    }


    private void buildArrange() {
        pendingList = new ArrayList<>();
        pendingList.add(new LaterRetry(UUID.randomUUID().toString()));
        pendingList.add(new LaterRetry(UUID.randomUUID().toString()));
        pendingList.add(new LaterRetry(UUID.randomUUID().toString()));
    }
}
