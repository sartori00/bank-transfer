package br.com.itau.banktransfer.infrastructure.entity;

import br.com.itau.banktransfer.infrastructure.entity.enums.RetryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "later_retry_entity")
public class LaterRetry {

    @Id
    private String id;

    private String idTransfer;

    private LocalDateTime createdAt;

    private RetryStatus retryStatus;

    public LaterRetry(String idTransfer){
        this.idTransfer = idTransfer;
        this.createdAt = LocalDateTime.now();
        this.retryStatus = RetryStatus.WAITING;
    }
}
