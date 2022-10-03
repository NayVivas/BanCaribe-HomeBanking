package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;
    private String numberAccount;
    private LocalDateTime creationDate;
    private double balance;
    private Boolean accountActive = true;

    private AccountType typeAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() {}


    public Account(String numberAccount, LocalDateTime creationDate, double balance, Client client, boolean accountActive, AccountType typeAccount) {
        this.numberAccount = numberAccount;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.accountActive = accountActive;
        this.typeAccount = typeAccount;
    }

    public long getId() {
        return id;
    }

    public String getNumberAccount() {
        return numberAccount;
    }
    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @JsonIgnore //Jackson transforma los datos a json
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Boolean getAccountActive() {return accountActive;}
    public void setAccountActive(Boolean accountActive) {this.accountActive = accountActive;}

    public AccountType getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(AccountType typeAccount) {
        this.typeAccount = typeAccount;
    }
}
