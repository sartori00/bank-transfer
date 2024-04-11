package br.com.itau.banktransfer.validation.impl;

import br.com.itau.banktransfer.exception.BusinessException;
import br.com.itau.banktransfer.util.ResponseMessages;
import br.com.itau.banktransfer.validation.ItemsForValidation;
import br.com.itau.banktransfer.validation.ValidationRules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DailyLimitValidation implements ValidationRules {

    @Override
    public void valid(ItemsForValidation itemsForValidation) {
        var originAccount = itemsForValidation.originAccount();
        var amountToTransfer = itemsForValidation.dto().amount();

        if(amountToTransfer.compareTo(originAccount.limiteDiario()) > 0 ) {
            log.error("Daily transaction limit has been exceeded for account {} - {}",
                    originAccount.id(), itemsForValidation.idTransfer());

            throw new BusinessException(ResponseMessages.TRANSACTION_LIMIT_EXCEEDED);
        }
    }
}
