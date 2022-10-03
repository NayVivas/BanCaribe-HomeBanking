package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.models.Account;
import java.util.List;

public interface AccountService {

    public List<Account> getAllAccounts();

    Account getAccountById(Long id);

    Account getNumberAccount(String numberAccount);

    void saveAccount(Account account);

}
