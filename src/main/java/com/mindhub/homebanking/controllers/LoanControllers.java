package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.*;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanControllers {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanService clientLoanService;


    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }


    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<String> createLoan (
            @RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Loan loan = loanService.getLoanById(loanApplicationDTO.getId());
        Account accountDestinyPayments = accountService.getNumberAccount(loanApplicationDTO.getDestinyNumberAccount());


        if (loanApplicationDTO.getId() == 0 || loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getDestinyNumberAccount().isEmpty() || loanApplicationDTO.getPayments() == 0) {
            return new ResponseEntity<>("enter all the requested data", HttpStatus.FORBIDDEN);
        }

        if (loan == null) {
            return new ResponseEntity<>(
                    "the loan does not exist", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("The amount to request exceeds the amount of the loan", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>(
                    "It is not possible to request that amount of quotas", HttpStatus.FORBIDDEN);
        }

        if (accountDestinyPayments == null) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccount().contains(accountDestinyPayments)){
            return new ResponseEntity<>("The account does not belong to the authenticated client",HttpStatus.FORBIDDEN);
        }

        double interestLoan = (loanApplicationDTO.getAmount() * loan.getInteresPercentage()) + loanApplicationDTO.getAmount();
        double paymentsLoan = Math.floor(interestLoan / loanApplicationDTO.getPayments());

        ClientLoan clientLoan = new ClientLoan(interestLoan,loanApplicationDTO.getPayments(),client,loan);
        Transaction transaction = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), loanApplicationDTO.getAmount(), loan.getName(), accountDestinyPayments);

        transactionService.saveTransaction(transaction);
        clientLoanService.saveClientLoan(clientLoan);

        /*double originAccountPayments = loanApplicationDTO.getAmount() - accountDestinyPayments.getBalance();*/
        double destinyAccountPayments = loanApplicationDTO.getAmount()+ accountDestinyPayments.getBalance();

        /*Account originAccount = accountService.getNumberAccount(accountDestinyPayments.getNumberAccount());*/
        Account destinyAccount = accountService.getNumberAccount(accountDestinyPayments.getNumberAccount());

        /*originAccount.setBalance(originAccountPayments);*/
        destinyAccount.setBalance(destinyAccountPayments);

        return new ResponseEntity<>("Prestamo exitoso",HttpStatus.CREATED);
    }

    @PostMapping("/loans/create")
    public ResponseEntity<Object> newLoan(Authentication authentication,
                                          @RequestParam String loanName, @RequestParam Double maxAmount, @RequestParam List<Integer> payments, @RequestParam Double percentage) {

        if (loanName.isEmpty() || (maxAmount.toString().isEmpty()) || (payments.isEmpty()) || percentage.toString().isEmpty()) {
            return new ResponseEntity<>("Debes rellenar todos los datos", HttpStatus.FORBIDDEN);
        }

        if (loanName.isEmpty()) {
            return new ResponseEntity<>("Debes ingresar un nombre para el prestamo", HttpStatus.FORBIDDEN);
        }

        if (maxAmount.toString().isEmpty()) {
            return new ResponseEntity<>("Debes ingresar un monto maximo para el prestamo", HttpStatus.FORBIDDEN);
        }

        if (payments.isEmpty()) {
            return new ResponseEntity<>("Debes ingresar las cuotas en que se puede pagar el prestamo", HttpStatus.FORBIDDEN);
        }

        if (percentage.toString().isEmpty()) {
            return new ResponseEntity<>("Debes ingresar el porcentaje de interes del prestamo", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findClientByEmail(authentication.getName());

        Loan newLoanAdmin = new Loan(loanName, maxAmount, payments, percentage);

        loanService.saveLoan(newLoanAdmin);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
