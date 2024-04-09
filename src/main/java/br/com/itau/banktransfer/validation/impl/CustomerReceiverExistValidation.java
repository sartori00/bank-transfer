package br.com.itau.banktransfer.validation.impl;

import br.com.itau.banktransfer.exception.BusinessException;
import br.com.itau.banktransfer.util.ResponseMessages;
import br.com.itau.banktransfer.validation.ItemsForValidation;
import br.com.itau.banktransfer.validation.ValidationRules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerReceiverExistValidation implements ValidationRules {

    @Override
    public void valid(ItemsForValidation itemsForValidation) {
        var destinationCustomer = itemsForValidation.destinationCustomer();

        if(!destinationCustomer.exist()){
            log.error("[CustomerReceiverExistValidation] - Customer {} not Found - {}", destinationCustomer.id(),
                    itemsForValidation.idTransfer());

            throw new BusinessException(ResponseMessages.CUSTOMER_NOT_FOUND);
        }
    }
}
