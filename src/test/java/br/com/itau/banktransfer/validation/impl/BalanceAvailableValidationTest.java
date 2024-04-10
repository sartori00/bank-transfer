package br.com.itau.banktransfer.validation.impl;

import br.com.itau.banktransfer.api.dto.AccountRequestDto;
import br.com.itau.banktransfer.api.dto.TransferRequestDto;
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
public class BalanceAvailableValidationTest {

    @Mock
    private ItemsForValidation itemsForValidation;

    @InjectMocks
    private BalanceAvailableValidation balanceAvailableValidation;

    AccountResponseDto account;

    TransferRequestDto transferLessMoney;
    TransferRequestDto transferMuchMoney;

    @BeforeEach
    void setUp() {
        this.buildDtos();
    }

    @Test
    public void validationShouldPassesForAccountWithEnoughBalanceAndDoesNotThrowsException() {
        when(itemsForValidation.originAccount()).thenReturn(account);
        when(itemsForValidation.dto()).thenReturn(transferLessMoney);

        assertDoesNotThrow(() -> balanceAvailableValidation.valid(itemsForValidation));
    }

    @Test
    public void validationShouldThrowsExceptionForAccountWithInsufficientBalance() {
        when(itemsForValidation.originAccount()).thenReturn(account);
        when(itemsForValidation.dto()).thenReturn(transferMuchMoney);

        var exception = assertThrows(BusinessException.class, () -> balanceAvailableValidation.valid(itemsForValidation));
        assert(exception.getMessage().equals(ResponseMessages.NO_BALANCE));
    }

    private void buildDtos(){
        account = new AccountResponseDto("d0d32142-74b7-4aca-9c68-838aeacef96b",
                BigDecimal.valueOf(1000),
                true,
                BigDecimal.valueOf(1000)
        );

        transferLessMoney = new TransferRequestDto("bcdd1048-a501-4608-bc82-66d7b4db3600",
                BigDecimal.valueOf(500),
                new AccountRequestDto("d0d32142-74b7-4aca-9c68-838aeacef96b",
                        "41313d7b-bd75-4c75-9dea-1f4be434007f")
        );

        transferMuchMoney = new TransferRequestDto("bcdd1048-a501-4608-bc82-66d7b4db3600",
                BigDecimal.valueOf(2000),
                new AccountRequestDto("d0d32142-74b7-4aca-9c68-838aeacef96b",
                        "41313d7b-bd75-4c75-9dea-1f4be434007f")
        );
    }
}
