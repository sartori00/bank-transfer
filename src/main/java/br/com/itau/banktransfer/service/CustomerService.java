package br.com.itau.banktransfer.service;


import br.com.itau.banktransfer.client.customer.CustomerClient;
import br.com.itau.banktransfer.client.customer.dto.CustomerResponseDto;
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

    public CustomerResponseDto getCustomer(String customerId){
        return customerClient.getCustomer(customerId);
    }


}
