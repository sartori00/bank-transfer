package br.com.itau.banktransfer.client.bacen;

import br.com.itau.banktransfer.client.bacen.dto.BacenNotificationDto;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${api.bacen.url}", name = "bacen")
@RateLimiter(name = "bacen")
public interface BacenClient {

    @PostMapping
    @Retry(name = "bacen")
    void notifyTransaction(BacenNotificationDto dto);
}
