package br.com.itau.banktransfer.api.v1.controller;

import br.com.itau.banktransfer.api.dto.AccountRequestDto;
import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import br.com.itau.banktransfer.service.TransferProcessService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferControllerTest {

    @Mock
    private TransferProcessService transferProcessService;

    @InjectMocks
    private TransferController transferController;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    TransferRequestDto requestDto;
    TransferResponseDto responseDto;
    AccountRequestDto account;

    @BeforeEach
    public void setUp() {
        this.buildArrange();
    }

    @Test
    public void shouldCallProcessTransfer() {
        when(transferProcessService.processTransfer(any(TransferRequestDto.class))).thenReturn(responseDto);

        var responseEntity = transferController.doTransfer(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto, responseEntity.getBody());
    }

    @Test
    public void shouldTestBeanValidatorsFromTransferRequestDto() {
        // Test transferRequestDto with null destinationCustomerId
        var transferRequestDto = new TransferRequestDto(null, BigDecimal.TEN, account);
        var violations = validator.validate(transferRequestDto);
        var violation = violations.stream().toList().get(0);

        assertEquals(1, violations.size());
        assertEquals(violation.getMessageTemplate(),"{jakarta.validation.constraints.NotNull.message}");
        assertEquals(violation.getPropertyPath().toString(),"destinationCustomerId");

        // Test transferRequestDto with null amount
        transferRequestDto = new TransferRequestDto("bcdd1048-a501-4608-bc82-66d7b4db3600", null, account);
        violations = validator.validate(transferRequestDto);
        violation = violations.stream().toList().get(0);

        assertEquals(1, violations.size());
        assertEquals(violation.getMessageTemplate(),"{jakarta.validation.constraints.NotNull.message}");
        assertEquals(violation.getPropertyPath().toString(),"amount");

        // Test transferRequestDto with null account
        transferRequestDto = new TransferRequestDto("bcdd1048-a501-4608-bc82-66d7b4db3600", BigDecimal.TEN, null);
        violations = validator.validate(transferRequestDto);
        violation = violations.stream().toList().get(0);

        assertEquals(1, violations.size());
        assertEquals(violation.getMessageTemplate(),"{jakarta.validation.constraints.NotNull.message}");
        assertEquals(violation.getPropertyPath().toString(),"account");

        // Test transferRequestDto with valid TransferRequestDto
        transferRequestDto = new TransferRequestDto("bcdd1048-a501-4608-bc82-66d7b4db3600", BigDecimal.TEN, account);
        violations = validator.validate(transferRequestDto);

        assertEquals(0, violations.size());
    }

    @Test
    public void shouldTestBeanValidatorsFromAccountRequestDto(){
        // Test AccountRequestDto with null originAccount
        var accountRequestDto = new AccountRequestDto(null, "41313d7b-bd75-4c75-9dea-1f4be434007f");
        var violations = validator.validate(accountRequestDto);
        var violation = violations.stream().toList().get(0);

        assertEquals(1, violations.size());
        assertEquals(violation.getMessageTemplate(),"{jakarta.validation.constraints.NotBlank.message}");
        assertEquals(violation.getPropertyPath().toString(),"originAccount");

        // Test AccountRequestDto with null toAccount
        accountRequestDto = new AccountRequestDto("d0d32142-74b7-4aca-9c68-838aeacef96b", null);
        violations = validator.validate(accountRequestDto);
        violation = violations.stream().toList().get(0);

        assertEquals(1, violations.size());
        assertEquals(violation.getMessageTemplate(),"{jakarta.validation.constraints.NotBlank.message}");
        assertEquals(violation.getPropertyPath().toString(),"toAccount");

        //Test AccountRequestDto with valid TransferRequestDto
        accountRequestDto = new AccountRequestDto("d0d32142-74b7-4aca-9c68-838aeacef96b", "41313d7b-bd75-4c75-9dea-1f4be434007f");
        violations = validator.validate(accountRequestDto);

        assertEquals(0, violations.size());
    }


    private void buildArrange() {
        account = new AccountRequestDto("d0d32142-74b7-4aca-9c68-838aeacef96b",
                "41313d7b-bd75-4c75-9dea-1f4be434007f"
        );
        requestDto = new TransferRequestDto("bcdd1048-a501-4608-bc82-66d7b4db3600",
                BigDecimal.TEN,
                account
        );

        responseDto = new TransferResponseDto(UUID.randomUUID());
    }
}
