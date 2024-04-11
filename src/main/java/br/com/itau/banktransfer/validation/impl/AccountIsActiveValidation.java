package br.com.itau.banktransfer.validation.impl;

import br.com.itau.banktransfer.exception.BusinessException;
import br.com.itau.banktransfer.util.ResponseMessages;
import br.com.itau.banktransfer.validation.ItemsForValidation;
import br.com.itau.banktransfer.validation.ValidationRules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountIsActiveValidation implements ValidationRules {

    @Override
    public void valid(ItemsForValidation itemsForValidation) {
        var originAccount = itemsForValidation.originAccount();

        if(!originAccount.ativo()){
            log.error("Account {} Inactive or Not Found - {}", originAccount.id(),
                    itemsForValidation.idTransfer());

            throw new BusinessException(ResponseMessages.ACCOUNT_INACTIVE);
        }
    }
}
