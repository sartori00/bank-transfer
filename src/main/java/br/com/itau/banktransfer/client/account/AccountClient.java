package br.com.itau.banktransfer.client.account;

import br.com.itau.banktransfer.client.account.dto.AccountResponseDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${api.account.url}", name = "account")
public interface AccountClient {

    @GetMapping("/{accountId}")
    @Retry(name = "account")
    AccountResponseDto getAccount(@PathVariable String accountId);

}
