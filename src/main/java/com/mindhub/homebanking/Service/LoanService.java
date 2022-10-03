package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import java.util.List;

public interface LoanService {

    public List<Loan> getAllLoans();

    Loan getLoanById(Long id);

    void saveLoan(Loan loan);
}
