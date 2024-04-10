package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.infrastructure.entity.LaterRetry;
import br.com.itau.banktransfer.infrastructure.entity.enums.RetryStatus;
import br.com.itau.banktransfer.infrastructure.repository.LaterRetryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaterRetryServiceTest {

    @Mock
    private LaterRetryRepository repository;

    @InjectMocks
    private LaterRetryService service;

    LaterRetry laterRetry;
    List<LaterRetry> pendingList;

    @BeforeEach
    void setUp() {
        this.buildArrange();
    }

    @Test
    void shouldSaveTLaterRetry() {
        service.save(laterRetry);

        verify(repository, times(ConstantTimes.ONLY_ONCE)).save(laterRetry);
    }

    @Test
    void shouldFindThreeLaterRetries() {
        when(repository.findByRetryStatus(RetryStatus.WAITING)).thenReturn(pendingList);

        var result = service.findPending();

        verify(repository, times(ConstantTimes.ONLY_ONCE)).findByRetryStatus(RetryStatus.WAITING);
        assert(result.size() == 3);
    }

    @Test
    void shouldDeleteAllOfPendingLaterRetries() {
        service.deleteAllOfThis(pendingList);

        verify(repository, times(ConstantTimes.THREE_TIMES)).delete(any());
    }

    private void buildArrange() {
        laterRetry = new LaterRetry(UUID.randomUUID().toString());

        pendingList = new ArrayList<>();
        pendingList.add(laterRetry);
        pendingList.add(new LaterRetry(UUID.randomUUID().toString()));
        pendingList.add(new LaterRetry(UUID.randomUUID().toString()));
    }
}
