package dev.kasckepperd.finance;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }
    @Transactional
    public Transaction NewTransaction(Transaction transaction, int accountId,Integer categoryId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
        if(categoryId!=null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            transaction.setCategory(category);
        }
        if(newBalance.compareTo(BigDecimal.ZERO) >= 0) {
            account.setBalance(newBalance);
            transaction.setAccount(account);
            accountRepository.save(account);
            return transactionRepository.save(transaction);
        }
        throw new IllegalArgumentException("No money!");
    }
    @Transactional
    public void NewTransfer(BigDecimal amount, int accountId1, int accountId2) {
        Account account1 = accountRepository.findById(accountId1).orElseThrow();
        Account account2 = accountRepository.findById(accountId2).orElseThrow();
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("This transfer amount must be greater than zero!");
        }
        if(account1.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("You don't have that amount!");
        }

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

    }

    public List<Transaction> ShowTransactions(int accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    List<CategorySpending> getSpendingbyCategories(@Param("accountId") int accountId){
        return transactionRepository.getSpendingbyCategories(accountId);
    }

    List<CategorySpending> getSpendingbyCategoriesMonth(@Param("accountId") int accountId, LocalDateTime start, LocalDateTime end){
        return transactionRepository.getSpendingByCategoriesMonth(accountId, start, end);
    }
}
