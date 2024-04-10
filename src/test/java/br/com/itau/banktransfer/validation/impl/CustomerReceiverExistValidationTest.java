package br.com.itau.banktransfer.validation.impl;

import br.com.itau.banktransfer.client.customer.dto.CustomerResponseDto;
import br.com.itau.banktransfer.exception.BusinessException;
import br.com.itau.banktransfer.util.ResponseMessages;
import br.com.itau.banktransfer.validation.ItemsForValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerReceiverExistValidationTest {

    @Mock
    private ItemsForValidation itemsForValidation;

    @InjectMocks
    private CustomerReceiverExistValidation customerReceiverExistValidation;

    CustomerResponseDto destinationExistsCustomer;
    CustomerResponseDto destinationNotExistsCustomer;

    @BeforeEach
    void setUp() {
        this.buildDtos();
    }

    @Test
    void validationShouldPassesWhenDestinationCustomerExistsAndDoesNotThrowsException() {
        when(itemsForValidation.destinationCustomer()).thenReturn(destinationExistsCustomer);

        assertDoesNotThrow(() -> customerReceiverExistValidation.valid(itemsForValidation));
    }

    @Test
    void validationShouldThrowsExceptionWhenDestinationCustomerDoesNotExist() {
        when(itemsForValidation.destinationCustomer()).thenReturn(destinationNotExistsCustomer);

        var exception = assertThrows(BusinessException.class, () -> customerReceiverExistValidation.valid(itemsForValidation));
        assert(exception.getMessage().equals(ResponseMessages.CUSTOMER_NOT_FOUND));
    }

    private void buildDtos(){
        destinationExistsCustomer = new CustomerResponseDto("bcdd1048-a501-4608-bc82-66d7b4db3600", true);
        destinationNotExistsCustomer = new CustomerResponseDto("bcdd1048-a501-4608-bc82-66d7b4db3600", false);
    }
}
