package br.com.itau.banktransfer.util;

public class ResponseMessages {
    public static final String ACCOUNT_INACTIVE = "Account Inactive or Not Found.";
    public static final String NO_BALANCE = "There is no balance available in the account";
    public static final String CUSTOMER_NOT_FOUND = "Customer informed not found";
    public static final String TRANSACTION_LIMIT_EXCEEDED = "Daily transaction limit has been exceeded";
    public static final String RATE_LIMIT_BLOCKED = "Bacen API blocked this request due to Rate Limit";
    public static final String TRANSACTION_IS_NOT_COMPLETED = "Transaction isn't completed, try again";

}
