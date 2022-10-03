package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.models.Transaction;
import java.util.List;

public interface TransactionService {

    public List<Transaction> getAllTransactions();

    Transaction getTransactionById(Long id);

    public void saveTransaction(Transaction transaction);
}
