package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.client.account.AccountClient;
import br.com.itau.banktransfer.client.account.dto.AccountResponseDto;
import br.com.itau.banktransfer.exception.FallbackException;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private AccountService accountService;

    AccountResponseDto expectedResponse;
    String accountId;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    void shouldGetAccountAndDoesNotThrowsException(){
        assertDoesNotThrow(() -> {
            accountService.getAccount(accountId);
        });
        verify(accountClient, times(ConstantTimes.ONLY_ONCE)).getAccount(any());
    }

    @Test
    void shouldGetAccountAndReturnExpectedResponse() {
        when(accountClient.getAccount(accountId)).thenReturn(expectedResponse);

        var actualResponse = accountService.getAccount(accountId);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldDoesNotThrowExceptionWhenAccountResponseIsNotFound() {
        FeignException.NotFound feignException = mock(FeignException.NotFound.class);
        assertDoesNotThrow(() -> {
            accountService.getAccountFallback(accountId, feignException);
        });
        verifyNoInteractions(accountClient);
    }

    @Test
    void shouldReturnActiveFalseWhenAccountResponseIsNotFound() {
        FeignException.NotFound feignException = mock(FeignException.NotFound.class);

        var accountResponseDto = accountService.getAccountFallback(accountId, feignException);
        assertEquals(accountResponseDto.ativo(), false);

        verifyNoInteractions(accountClient);
    }

    @Test
    void shouldCallFallbackMethodWhenAnErrorIsThrownAndThrowsFallbackException() {
        Throwable cause = mock(Throwable.class);
        assertThrows(FallbackException.class, () -> {
            accountService.getAccountFallback(accountId, cause);
        });
        verifyNoInteractions(accountClient);
    }

    private void buildArranges() {
        accountId = "d0d32142-74b7-4aca-9c68-838aeacef96b";
        expectedResponse = new AccountResponseDto(accountId,
                BigDecimal.valueOf(1000),
                true,
                BigDecimal.valueOf(500)
        );
    }
}
