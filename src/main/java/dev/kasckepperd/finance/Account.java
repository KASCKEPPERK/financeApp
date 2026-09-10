package dev.kasckepperd.finance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "accounts")

public class Account {
    @Id
    @GeneratedValue

    private int id;
    private BigDecimal balance;
    private String currency;
    private String type;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
