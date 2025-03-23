package vn.bighousevn.jobhunter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.bighousevn.jobhunter.domain.Account;
import vn.bighousevn.jobhunter.service.AccountService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/")
public class AccountController {

    private final AccountService accountService;
    public AccountController(AccountService accountService)
    {this.accountService = accountService;}


    @GetMapping("accounts")
    public List<Account> getAllAccounts() {
        return this.accountService.fetchAll();
    }
    
    @PostMapping("accounts")
    public String createAccount(@RequestBody Account account) {
        return this.accountService.create(account);
    }
    
}
