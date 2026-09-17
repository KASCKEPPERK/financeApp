package dev.kasckepperd.finance;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")

public class AccountController {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountController(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/user/{userId}")
    public Account createAccount(@RequestBody Account account,@PathVariable int userId) {
        User user = userRepository.findById(userId).orElseThrow();
        account.setUser(user);
        return accountRepository.save(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("user/{userId}")
    public List<Account> getAccountsByUserId(@PathVariable int userId) {
        return accountRepository.findByUserId(userId);
    }
}
