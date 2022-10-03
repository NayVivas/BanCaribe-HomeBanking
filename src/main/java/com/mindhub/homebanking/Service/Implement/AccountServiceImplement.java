package com.mindhub.homebanking.Service.Implement;

import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public Account getNumberAccount(String numberAccount) {
        return accountRepository.findByNumberAccount(numberAccount);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

}
