package dev.kasckepperd.finance;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransferRequest {
        BigDecimal amount;
        int targetAccountId;

        public TransferRequest(BigDecimal amount, int targetAccountId) {
            this.amount = amount;
            this.targetAccountId = targetAccountId;
        }


}
