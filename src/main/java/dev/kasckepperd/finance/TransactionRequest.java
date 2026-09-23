package dev.kasckepperd.finance;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionRequest {

    BigDecimal amount;
    String description;
    Integer categoryId;

    public TransactionRequest(BigDecimal amount, String description, Integer categoryId) {
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
    }


}
