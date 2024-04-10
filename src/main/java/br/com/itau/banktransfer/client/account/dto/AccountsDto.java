package br.com.itau.banktransfer.client.account.dto;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;

public record AccountsDto(
        String idOrigem,
        String idDestino
) {
    public AccountsDto(Transaction transaction) {
        this(transaction.getOriginAccount(), transaction.getToAccount());
    }
}
