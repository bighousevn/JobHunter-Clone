package vn.bighousevn.jobhunter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.bighousevn.jobhunter.domain.Account;
import vn.bighousevn.jobhunter.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    public AccountService( AccountRepository accountRepository)
    {this.accountRepository = accountRepository;}
    

    public List<Account> fetchAll() {
        return this.accountRepository.findAll();
    }

    public String create(Account acc){
        this.accountRepository.save(acc);
        return "good";
    }
}
