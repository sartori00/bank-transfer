package br.com.itau.banktransfer.client.bacen.dto;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;

public record NotificationAccountsDto(
        String idOrigem,
        String idDestino
) {
    public NotificationAccountsDto(Transaction transaction) {
        this(transaction.getOriginAccount(), transaction.getToAccount());
    }
}
