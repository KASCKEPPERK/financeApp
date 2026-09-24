package dev.kasckepperd.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Query("""
    SELECT new dev.kasckepperd.finance.CategorySpending(t.category.name,SUM(t.amount))
    FROM Transaction t
    WHERE t.account.id = :accountId
    AND t.amount < 0
    AND t.timestamp > :start
    AND t.timestamp < :end
    GROUP BY t.category.name

""")
    List<CategorySpending> getSpendingByCategoriesMonth(@Param("accountId") int accountId, @Param("start") LocalDateTime start, @Param("end")LocalDateTime end);

}
