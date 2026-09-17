package dev.kasckepperd.finance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue

    private int id;
    private BigDecimal amount;
    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

}
