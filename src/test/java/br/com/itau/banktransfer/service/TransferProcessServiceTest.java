package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.api.dto.AccountRequestDto;
import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.validation.ValidationRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferProcessServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private AccountService accountService;

    @Mock
    private List<ValidationRules> validationRulesList;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TransferProcessService transferProcessService;

    @Captor
    ArgumentCaptor<Transaction> transaction;

    TransferRequestDto dto;

    @BeforeEach
    void setUp() {
        dto = this.buildDto();
    }

    @Test
    void shouldExecuteAllInteractionsWhenSuccessfulProcessing() {
        when(transactionService.save(any())).thenReturn(new Transaction());
        doNothing().when(eventPublisher).publishEvent(any());

        var responseDto = transferProcessService.processTransfer(dto);

        verify(customerService, times(ConstantTimes.ONLY_ONCE)).getCustomer(anyString());
        verify(accountService, times(ConstantTimes.ONLY_ONCE)).getAccount(anyString());
        verify(validationRulesList, times(ConstantTimes.ONLY_ONCE)).forEach(any());
        verify(transactionService, times(ConstantTimes.ONLY_ONCE)).save(transaction.capture());

        var transactionSaved = transaction.getValue();

        assertEquals(transactionSaved.getIdTransfer(), responseDto.idTransfer().toString());
        assertEquals(dto.destinationCustomerId(), transactionSaved.getDestinationCustomerId());
        assertEquals(dto.amount(), transactionSaved.getAmount());
        assertEquals(dto.account().originAccount(), transactionSaved.getOriginAccount());
        assertEquals(dto.account().toAccount(), transactionSaved.getToAccount());

        verify(eventPublisher, times(ConstantTimes.ONLY_ONCE)).publishEvent(any());
    }

    @Test
    void shouldThrowsExceptionWhenTransactionDoesNotInsertedInDatabase() {
        when(transactionService.save(any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> transferProcessService.processTransfer(dto));
        verify(validationRulesList, times(ConstantTimes.ONLY_ONCE)).forEach(any());
        verify(transactionService, times(ConstantTimes.ONLY_ONCE)).save(any());
        verifyNoInteractions(eventPublisher);
    }

    private TransferRequestDto buildDto(){
        return new TransferRequestDto("bcdd1048-a501-4608-bc82-66d7b4db3600",
                BigDecimal.valueOf(500), new AccountRequestDto("d0d32142-74b7-4aca-9c68-838aeacef96b",
                        "41313d7b-bd75-4c75-9dea-1f4be434007f"));
    }
}
