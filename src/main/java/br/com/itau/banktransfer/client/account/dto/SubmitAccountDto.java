package br.com.itau.banktransfer.client.account.dto;

import br.com.itau.banktransfer.infrastructure.entity.Transaction;

import java.math.BigDecimal;

public record SubmitAccountDto(
        BigDecimal valor,
        AccountsDto conta
) {

    public SubmitAccountDto(Transaction transaction) {
        this(transaction.getAmount(), new AccountsDto(transaction));
    }
}
