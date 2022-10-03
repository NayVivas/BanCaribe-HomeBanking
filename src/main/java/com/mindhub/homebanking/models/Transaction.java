package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    //propiedades
    private long id;
    private TransactionType type;
    private LocalDateTime creationDateTransaction;
    private double amount;
    private String description;

    //Relacion muchos a uno
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;


    //constructores
    public Transaction() {
    }
    public Transaction(TransactionType type, LocalDateTime creationDateTransaction, double amount, String description, Account account) { //argumentos
        this.type = type;
        this.creationDateTransaction = creationDateTransaction;
        this.amount = amount;
        this.description = description;
        this.account = account;
    }

    //getter and setters
    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getCreationDateTransaction() {
        return creationDateTransaction;
    }
    public void setCreationDateTransaction(LocalDateTime creationDateTransaction) {
        this.creationDateTransaction = creationDateTransaction;
    }

    public double getAmount() { return amount; }
    public void setBalanceTransaction(int balanceTransaction) {
        this.amount = balanceTransaction;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
