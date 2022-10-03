package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;
    private String numberAccount;
    private LocalDateTime creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;
    private Boolean accountActive = true;

    private AccountType typeAccount;

    //contructores
    public AccountDTO() {
    }
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.numberAccount = account.getNumberAccount();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.accountActive = account.getAccountActive();
        this.typeAccount = account.getTypeAccount();
    }

    public long getId() {
        return id;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public Boolean getAccountActive() {
        return accountActive;
    }

    public AccountType getTypeAccount() {
        return typeAccount;
    }
}
