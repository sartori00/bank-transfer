package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.infrastructure.repository.TransactionRepository;
import br.com.itau.banktransfer.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionServiceImpl service;

    private String idTransfer;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    public void shouldSaveTransaction() {
        when(repository.save(transaction)).thenReturn(transaction);

        var savedTransaction = service.save(transaction);

        verify(repository, times(ConstantTimes.ONLY_ONCE)).save(transaction);
        assertEquals(transaction, savedTransaction);
    }

    @Test
    public void shouldFindATransactionByIdTransfer() {
        when(repository.findByIdTransfer(idTransfer)).thenReturn(Optional.of(transaction));

        var foundTransaction = service.findByIdTransfer(idTransfer);

        verify(repository, times(ConstantTimes.ONLY_ONCE)).findByIdTransfer(idTransfer);
        assertEquals(transaction, foundTransaction);
    }

    @Test
    public void shouldThrowsNoSuchElementExceptionWhenNoTransactionIsFound() {
        when(repository.findByIdTransfer(idTransfer)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findByIdTransfer(idTransfer));
        verify(repository, times(ConstantTimes.ONLY_ONCE)).findByIdTransfer(idTransfer);
    }

    private void buildArranges() {
        idTransfer = UUID.randomUUID().toString();

        transaction = new Transaction.TransactionBuilder()
                .destinationCustomerId("bcdd1048-a501-4608-bc82-66d7b4db3600")
                .amount(BigDecimal.TEN)
                .originAccount("d0d32142-74b7-4aca-9c68-838aeacef96b")
                .toAccount("41313d7b-bd75-4c75-9dea-1f4be434007f")
                .idTransfer(idTransfer)
                .build();
    }
}
