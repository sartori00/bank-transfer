package br.com.itau.banktransfer.service.impl;

import br.com.itau.banktransfer.api.dto.TransferRequestDto;
import br.com.itau.banktransfer.api.dto.TransferResponseDto;
import br.com.itau.banktransfer.event.NewTransactionSavedEvent;
import br.com.itau.banktransfer.infrastructure.entity.Transaction;
import br.com.itau.banktransfer.service.AccountService;
import br.com.itau.banktransfer.service.CustomerService;
import br.com.itau.banktransfer.service.TransactionService;
import br.com.itau.banktransfer.service.TransferProcessService;
import br.com.itau.banktransfer.validation.ItemsForValidation;
import br.com.itau.banktransfer.validation.ValidationRules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransferProcessServiceImpl implements TransferProcessService {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final List<ValidationRules> validationRulesList;
    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public TransferProcessServiceImpl(CustomerService customerService, AccountService accountService,
                                      List<ValidationRules> validationRulesList, TransactionService transactionService,
                                      ApplicationEventPublisher eventPublisher) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.validationRulesList = validationRulesList;
        this.transactionService = transactionService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public TransferResponseDto processTransfer(TransferRequestDto dto) {
        UUID idTransfer = UUID.randomUUID();

        log.info("Started process transfer  {}", idTransfer);

        var itemsForValidation = this.getItemsForValidation(dto, idTransfer);
        validationRulesList.forEach(validationRule -> validationRule.valid(itemsForValidation));

        log.info("Rules Validated {}", idTransfer);

        var transaction = transactionService.save(this.transactionBuilder(dto, idTransfer));

        eventPublisher.publishEvent(new NewTransactionSavedEvent(this, transaction));

        log.info("Finished process transfer  {}", idTransfer);
        return new TransferResponseDto(idTransfer);
    }

    private Transaction transactionBuilder(TransferRequestDto dto, UUID idTransfer) {
        return new Transaction.TransactionBuilder()
                .destinationCustomerId(dto.destinationCustomerId())
                .amount(dto.amount())
                .originAccount(dto.account().originAccount())
                .toAccount(dto.account().toAccount())
                .idTransfer(idTransfer.toString())
                .build();
    }

    private ItemsForValidation getItemsForValidation(TransferRequestDto dto, UUID idTransfer) {
        var destinationCustomer = customerService.getCustomer(dto.destinationCustomerId());
        var originAccount = accountService.getAccount(dto.account().originAccount());

        return new ItemsForValidation(destinationCustomer, originAccount, dto, idTransfer);
    }

}
