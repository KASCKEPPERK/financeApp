package dev.kasckepperd.finance;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CategorySpending {
    String name;
    BigDecimal total;

    public CategorySpending(String name, BigDecimal total) {
        this.name = name;
        this.total = total;
    }
}
