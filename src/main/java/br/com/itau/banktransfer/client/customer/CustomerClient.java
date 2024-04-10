package br.com.itau.banktransfer.client.customer;

import br.com.itau.banktransfer.client.customer.dto.CustomerResponseDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${api.customer.url}", name = "customer")
public interface CustomerClient {

    @GetMapping("/{idCliente}")
    @Retry(name = "customer")
    CustomerResponseDto getCustomer(@PathVariable String idCliente);
}
