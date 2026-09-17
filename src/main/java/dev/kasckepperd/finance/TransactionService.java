package dev.kasckepperd.finance;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction NewTransaction(Transaction transaction, int accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
        if(newBalance.compareTo(BigDecimal.ZERO) >= 0) {
            account.setBalance(transaction.getAccount().getBalance().add(transaction.getAmount()));
            transaction.setAccount(account);
            accountRepository.save(account);
            return transactionRepository.save(transaction);
        }
        throw new IllegalArgumentException("No money!");
    }

    public ResponseEntity NewTransfer(BigDecimal amount, int accountId1, int accountId2) {
        Account account1 = accountRepository.findById(accountId1).orElseThrow();
        Account account2 = accountRepository.findById(accountId2).orElseThrow();
        if(account1.getBalance().compareTo(amount) >= 0 && amount.compareTo(BigDecimal.ZERO) > 0) {
            account1.setBalance(account1.getBalance().subtract(amount));
            accountRepository.save(account1);
            account2.setBalance(account2.getBalance().add(amount));
            accountRepository.save(account2);

            Transaction outgoing = new Transaction();
            outgoing.setAmount(amount.negate());
            outgoing.setDescription("Transfer");
            outgoing.setAccount(account1);

            Transaction incoming = new Transaction();
            incoming.setAmount(amount);
            incoming.setDescription("Transfer");
            incoming.setAccount(account2);

            transactionRepository.save(outgoing);
            transactionRepository.save(incoming);

            return ResponseEntity.ok(
                    Map.of("message", "Transfer successful!")
            );

        }
        return ResponseEntity.badRequest().body(
                Map.of("message", "You don't have that amount!")
        );
    }

    public List<Transaction> ShowTransactions(int accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
