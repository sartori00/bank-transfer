package br.com.itau.banktransfer.client.account;

import br.com.itau.banktransfer.client.account.dto.AccountResponseDto;
import br.com.itau.banktransfer.client.account.dto.SubmitAccountDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(url = "${api.account.url}", name = "account")
public interface AccountClient {

    @GetMapping("/{accountId}")
    @Retry(name = "account")
    AccountResponseDto getAccount(@PathVariable String accountId);

    @PutMapping("/saldos")
    @Retry(name = "transaction")
    void submitTransaction(SubmitAccountDto dto);
}
