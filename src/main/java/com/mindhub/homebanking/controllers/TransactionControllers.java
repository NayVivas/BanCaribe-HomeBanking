package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionControllers {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")

    public ResponseEntity<Object> registetTransaction(Authentication authentication,
                                                      @RequestParam Double amount,
                                                      @RequestParam String description,
                                                      @RequestParam String originNumber,
                                                      @RequestParam String destinyNumber) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Account originAccount = accountService.getNumberAccount(originNumber);
        Account destinyAccount = accountService.getNumberAccount(destinyNumber);

        if (amount.toString().isEmpty() || description.isEmpty() || originNumber.isEmpty() || destinyNumber.isEmpty()) {
            return new ResponseEntity<>("Parametro vacio", HttpStatus.FORBIDDEN);
        }

        if (originAccount == null) {
            return new ResponseEntity<>("la cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        if (originAccount == destinyAccount) {
            return new ResponseEntity<>("Las cuentas son iguales", HttpStatus.FORBIDDEN);
        }

        List<Account> listOriginAccount = client.getAccount().stream().filter(account -> account.getNumberAccount().equals(originNumber)).collect(Collectors.toList());

        if (listOriginAccount.size() == 0) {
            return new ResponseEntity<>("la cuenta de origen no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        if (destinyAccount == null) {
            return new ResponseEntity<>("la cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        if(originAccount.getBalance() < amount) {
            return new ResponseEntity<>("cliente no tiene fondos", HttpStatus.FORBIDDEN);
        }

        Transaction originTransactions = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), amount, description, originAccount);
        Transaction destinyTransactions = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), amount, description, destinyAccount);

        originAccount.setBalance(originAccount.getBalance() - amount);
        destinyAccount.setBalance(destinyAccount.getBalance() + amount);

        transactionService.saveTransaction(originTransactions);
        transactionService.saveTransaction(destinyTransactions);
        clientService.saveClient(client);

        return new ResponseEntity<>("Transaccion exitosa", HttpStatus.CREATED);
    }
}
