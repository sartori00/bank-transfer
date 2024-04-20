package br.com.itau.banktransfer.service.impl;

import br.com.itau.banktransfer.client.customer.CustomerClient;
import br.com.itau.banktransfer.client.customer.dto.CustomerResponseDto;
import br.com.itau.banktransfer.exception.FallbackException;
import br.com.itau.banktransfer.service.CustomerService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerClient customerClient;

    @Override
    @CircuitBreaker(name = "customer", fallbackMethod = "getCustomerFallback")
    public CustomerResponseDto getCustomer(String customerId){
        return customerClient.getCustomer(customerId);
    }

    @Override
    public CustomerResponseDto getCustomerFallback(String customerId, Throwable cause){
        if(cause instanceof FeignException.NotFound){
            return new CustomerResponseDto(customerId, false);
        }

        log.error("Fallback Customer with customerId {}", customerId);
        throw new FallbackException(cause);
    }
}
