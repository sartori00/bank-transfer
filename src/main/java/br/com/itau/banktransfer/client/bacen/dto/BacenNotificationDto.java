package br.com.itau.banktransfer.client.bacen.dto;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;

import java.math.BigDecimal;

public record BacenNotificationDto(
        BigDecimal valor,
        NotificationAccountsDto conta
) {

    public BacenNotificationDto(Transaction transaction) {
        this(transaction.getAmount(), new NotificationAccountsDto(transaction));
    }
}
