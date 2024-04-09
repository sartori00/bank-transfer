package br.com.itau.banktransfer.infrastructure.entity;

import br.com.itau.banktransfer.infrastructure.entity.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction_entity")
public class Transaction {

    @Id
    private String id;

    private String destinationCustomerId;

    private BigDecimal amount;

    private String originAccount;

    private String toAccount;

    private TransactionStatus status;

    private LocalDateTime processedAt;

    private LocalDateTime transferredAt;

    private LocalDateTime sentToBacenAt;

    private String idTransfer;

    private Transaction(String destinationCustomerId, BigDecimal amount, String originAccount, String toAccount, String idTransfer) {
        this.destinationCustomerId = destinationCustomerId;
        this.amount = amount;
        this.originAccount = originAccount;
        this.toAccount = toAccount;
        this.idTransfer = idTransfer;
        this.status = TransactionStatus.PROCESSED;
        this.processedAt = LocalDateTime.now();
    }


    public void setSentToBacen(){
        this.status = TransactionStatus.SENT_TO_BACEN;
        this.sentToBacenAt = LocalDateTime.now();
    }

    public void setTransferred(){
        this.status = TransactionStatus.TRANSFERRED;
        this.transferredAt = LocalDateTime.now();
    }

    // Builder Design Pattern
    public static class TransactionBuilder {
        private String destinationCustomerId;
        private BigDecimal amount;
        private String originAccount;
        private String toAccount;
        private String idTransfer;


        public TransactionBuilder destinationCustomerId(String destinationCustomerId){
            this.destinationCustomerId = destinationCustomerId;
            return this;
        }

        public TransactionBuilder amount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public TransactionBuilder originAccount(String originAccount){
            this.originAccount = originAccount;
            return this;
        }

        public TransactionBuilder toAccount(String toAccount){
            this.toAccount = toAccount;
            return this;
        }

        public TransactionBuilder idTransfer(String idTransfer){
            this.idTransfer = idTransfer;
            return this;
        }

        public Transaction build() {
            return new Transaction(destinationCustomerId, amount, originAccount, toAccount, idTransfer);
        }
    }
}
