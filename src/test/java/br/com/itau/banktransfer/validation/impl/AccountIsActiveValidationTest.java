package br.com.itau.banktransfer.validation.impl;

import br.com.itau.banktransfer.client.account.dto.AccountResponseDto;
import br.com.itau.banktransfer.exception.BusinessException;
import br.com.itau.banktransfer.util.ResponseMessages;
import br.com.itau.banktransfer.validation.ItemsForValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountIsActiveValidationTest {

    @Mock
    private ItemsForValidation itemsForValidation;

    @InjectMocks
    private AccountIsActiveValidation accountIsActiveValidation;

    private AccountResponseDto activeAccount;
    private AccountResponseDto inactiveAccount;

    @BeforeEach
    void setUp() {
        this.buildDtos();
    }

    @Test
    void validationShouldPassesForActiveAccountAndDoesNotThrowsException() {
        when(itemsForValidation.originAccount()).thenReturn(activeAccount);

        assertDoesNotThrow(() -> accountIsActiveValidation.valid(itemsForValidation));
    }

    @Test
    void validationShouldThrowsExceptionForInactiveAccount() {
        when(itemsForValidation.originAccount()).thenReturn(inactiveAccount);

        var exception = assertThrows(BusinessException.class, () -> accountIsActiveValidation.valid(itemsForValidation));
        assert(exception.getMessage().equals(ResponseMessages.ACCOUNT_INACTIVE));
    }

    private void buildDtos(){
        activeAccount = new AccountResponseDto("d0d32142-74b7-4aca-9c68-838aeacef96b",
                BigDecimal.valueOf(2000),
                true,
                BigDecimal.valueOf(1000)
        );

        inactiveAccount = new AccountResponseDto("d0d32142-74b7-4aca-9c68-838aeacef96b",
                BigDecimal.valueOf(2000),
                false,
                BigDecimal.valueOf(1000)
        );
    }
}
