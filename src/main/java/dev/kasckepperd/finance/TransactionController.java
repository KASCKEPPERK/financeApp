package dev.kasckepperd.finance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/service/{accountId}")
    public Transaction makeNewTransaction(@RequestBody Transaction transaction, @PathVariable int accountId) {
        return transactionService.NewTransaction(transaction, accountId);
    }

    @GetMapping("/{accountId}/history")
    public List<Transaction> getShowTransactions(@PathVariable int accountId) {
        return transactionService.ShowTransactions(accountId);
    }

    public record TransferRequest(
            BigDecimal amount,
            int targetAccountId

    ){}

    @PostMapping("/transfer/{accountId}")
    public ResponseEntity makeNewTransfer(@RequestBody TransferRequest request, @PathVariable int accountId) {
        return transactionService.NewTransfer(request.amount, request.targetAccountId, accountId);
    }


}
