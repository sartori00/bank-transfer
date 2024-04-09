package br.com.itau.banktransfer.service;


import br.com.itau.banktransfer.client.customer.CustomerClient;
import br.com.itau.banktransfer.client.customer.dto.CustomerResponseDto;
import br.com.itau.banktransfer.exception.FallbackException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService {

    private final CustomerClient customerClient;

    @Autowired
    public CustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @CircuitBreaker(name = "customer", fallbackMethod = "getCustomerFallback")
    public CustomerResponseDto getCustomer(String customerId){
        return customerClient.getCustomer(customerId);
    }

    public CustomerResponseDto getCustomerFallback(String customerId, Throwable cause){
        if(cause instanceof FeignException.NotFound){
            return new CustomerResponseDto(customerId, false);
        }

        log.error("[CustomerService] fallback Customer with customerId {}", customerId);
        throw new FallbackException(cause);
    }


}
