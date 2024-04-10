package br.com.itau.banktransfer.api.v1.controller;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.schedule.LaterRetrySchedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetryControllerTest {

    @Mock
    private LaterRetrySchedule laterRetrySchedule;

    @InjectMocks
    private RetryController retryController;


    @Test
    void shouldCallRetryScheduled() {
        doNothing().when(laterRetrySchedule).retryScheduled();

        var responseEntity = retryController.retryScheduled();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        verify(laterRetrySchedule, times(ConstantTimes.ONLY_ONCE)).retryScheduled();
    }
}

