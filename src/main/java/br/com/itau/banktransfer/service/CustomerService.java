package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.client.customer.dto.CustomerResponseDto;

public interface CustomerService {
    CustomerResponseDto getCustomer(String customerId);

    CustomerResponseDto getCustomerFallback(String customerId, Throwable cause);
}
