package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.ConstantTimes;
import br.com.itau.banktransfer.client.customer.CustomerClient;
import br.com.itau.banktransfer.client.customer.dto.CustomerResponseDto;
import br.com.itau.banktransfer.exception.FallbackException;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerClient customerClient;

    @InjectMocks
    private CustomerService customerService;

    CustomerResponseDto expectedResponse;
    String customerId;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    void shouldGetAccountAndDoesNotThrowsException(){
        assertDoesNotThrow(() -> {
            customerService.getCustomer(customerId);
        });
        verify(customerClient, times(ConstantTimes.ONLY_ONCE)).getCustomer(any());
    }

    @Test
    void shouldGetCustomerAndReturnExpectedResponse() {
        when(customerClient.getCustomer(customerId)).thenReturn(expectedResponse);

        var actualResponse = customerService.getCustomer(customerId);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldDoesNotThrowExceptionWhenCustomerResponseIsNotFound() {
        FeignException.NotFound feignException = mock(FeignException.NotFound.class);
        assertDoesNotThrow(() -> {
            customerService.getCustomerFallback(customerId, feignException);
        });
        verifyNoInteractions(customerClient);
    }

    @Test
    void shouldReturnActiveFalseWhenCustomerResponseIsNotFound() {
        FeignException.NotFound feignException = mock(FeignException.NotFound.class);

        var customerResponseDto = customerService.getCustomerFallback(customerId, feignException);
        assertEquals(customerResponseDto.exist(), false);

        verifyNoInteractions(customerClient);
    }

    @Test
    void shouldCallFallbackMethodWhenAnErrorIsThrownAndThrowsFallbackException() {
        Throwable cause = mock(Throwable.class);
        assertThrows(FallbackException.class, () -> {
            customerService.getCustomerFallback(customerId, cause);
        });
        verifyNoInteractions(customerClient);
    }

    private void buildArranges(){
        customerId = "bcdd1048-a501-4608-bc82-66d7b4db3600";
        expectedResponse = new CustomerResponseDto(customerId,
                "Rodrigo Sartori",
                "986703828",
                "Fisica",
                true
        );
    }
}