package br.com.itau.banktransfer.validation.impl;

import br.com.itau.banktransfer.exception.BusinessException;
import br.com.itau.banktransfer.util.ResponseMessages;
import br.com.itau.banktransfer.validation.ItemsForValidation;
import br.com.itau.banktransfer.validation.ValidationRules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BalanceAvailableValidation implements ValidationRules {

    @Override
    public void valid(ItemsForValidation itemsForValidation) {
        var originAccount = itemsForValidation.originAccount();
        var amountToTransfer = itemsForValidation.dto().amount();

        if(originAccount.saldo().compareTo(amountToTransfer) < 0){
            log.error("There is no balance available in the account {} - {}",
                    originAccount.id(), itemsForValidation.idTransfer());

            throw new BusinessException(ResponseMessages.NO_BALANCE);
        }
    }
}
