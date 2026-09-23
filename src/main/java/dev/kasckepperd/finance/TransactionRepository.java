package dev.kasckepperd.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccountId(int accountId);

    @Query("""
    SELECT new dev.kasckepperd.finance.CategorySpending(t.category.name,SUM(t.amount))
    FROM Transaction t
    WHERE t.account.id = :accountId
    AND t.amount < 0
    GROUP BY t.category.name
    """)
    List<CategorySpending> getSpendingbyCategories(@Param("accountId") int accountId);

}
