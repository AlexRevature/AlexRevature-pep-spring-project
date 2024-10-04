package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account persistAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account retrieveByUsername(String username) {
        List<Account> authList = accountRepository.findByUsername(username);
        if (authList != null && authList.size() == 1) {
            return authList.get(0);
        }
        return null;
    }

    public Account authAccount(String username, String password) {
        List<Account> authList = accountRepository.findByUsernameAndPassword(username, password);
        if (authList != null && authList.size() == 1) {
            return authList.get(0);
        } else {
            return null;
        }
    }

    
}
