package dev.kasckepperd.finance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/service/{accountId}")
    public Transaction makeNewTransaction(@RequestBody TransactionRequest request, @PathVariable int accountId) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount);
        transaction.setDescription(request.description);
        return transactionService.NewTransaction(transaction, accountId,request.categoryId);
    }


    @GetMapping("/{accountId}/history")
    public List<Transaction> getShowTransactions(@PathVariable int accountId) {
        return transactionService.ShowTransactions(accountId);
    }


    @PostMapping("/transfer/{accountId}")
    public ResponseEntity makeNewTransfer(@RequestBody TransferRequest request, @PathVariable int accountId) {
        transactionService.NewTransfer(request.amount, accountId, request.targetAccountId);
        return ResponseEntity.ok(
                Map.of("message", "Transfer successful!")
        );
    }

    @GetMapping("/{accountId}/analytics")
    public List<CategorySpending> getSpenddingAllCategories(@PathVariable int accountId) {
        return transactionService.getSpendingbyCategories(accountId);
    }


}
