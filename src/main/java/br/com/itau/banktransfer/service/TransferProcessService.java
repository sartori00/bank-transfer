package br.com.itau.banktransfer.service;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import br.com.itau.banktransfer.validation.ItemsForValidation;
import br.com.itau.banktransfer.validation.ValidationRules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransferProcessService {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final List<ValidationRules> validationRulesList;

    @Autowired
    public TransferProcessService(CustomerService customerService, AccountService accountService,
                                  List<ValidationRules> validationRulesList) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.validationRulesList = validationRulesList;
    }

    public TransferResponseDto processTransfer(TransferRequestDto dto) {
        UUID idTransfer = UUID.randomUUID();

        log.info("[TransferProcessService] Started process transfer  {}", idTransfer);

        var itemsForValidation = this.getItemsForValidation(dto, idTransfer);
        validationRulesList.forEach(validationRule -> validationRule.valid(itemsForValidation));

        log.info("[TransferProcessService] Rules Validated {}", idTransfer);

        return new TransferResponseDto(idTransfer);
    }

    private ItemsForValidation getItemsForValidation(TransferRequestDto dto, UUID idTransfer) {
        var destinationCustomer = customerService.getCustomer(dto.destinationCustomerId());
        var originAccount = accountService.getAccount(dto.account().originAccount());

        return new ItemsForValidation(destinationCustomer, originAccount, dto, idTransfer);
    }

}
