package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//Escuha y responde peticiones

@RestController
@RequestMapping("/api")

public class AccountControllers {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {                               //se ejecuta metodo get para devolver una lista de clientes
        return accountService.getAllAccounts().stream().map(account ->
                new AccountDTO(account)).collect(Collectors.toList());    //tengo un string y lo que necesito es una coleccion. colecciono los clientesDto para pasarlo a lista
        //retorna todos las cuentas en una lista
    }

    @GetMapping("accounts/{id}") //{id} variable de ruta
    public AccountDTO getAccount(@PathVariable Long id) { //lo procesa para transformarlo en un Long y asignarlo al parámetro code de la función
        return new AccountDTO(accountService.getAccountById(id));
    }

    int min = 0;
    int max = 99999999;
    public int getRandomNumber(int min,int max){return (int) ((Math.random() * (max - min)) + min);}

    public String accountString() {
        int accountNumber = getRandomNumber(min, max);
        return String.valueOf(accountNumber);
    }

    @GetMapping("clients/current/accounts")
    public List<AccountDTO> getAccountDto(Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        return client.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }


    @PostMapping("clients/current/accounts")
    public ResponseEntity<Object> createAccount(
            @RequestParam AccountType accountType,
            Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());

        List<AccountDTO> accountDTO = client.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        List<AccountDTO> accountDTOtrue = accountDTO.stream().filter(AccountDTO::getAccountActive).collect(Collectors.toList());

        if(accountDTOtrue.size() >= 3) {
            return new ResponseEntity<>("Limite de cuentas, ya no puedes crear mas cuentas", HttpStatus.FORBIDDEN);
        }

        Account account = new Account("VIN" + accountString(),LocalDateTime.now(), 0, client, true, accountType);

        accountService.saveAccount(account);
        clientService.saveClient(client);

        return new ResponseEntity<>("Cuenta creada con exito", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts/delete/{id}")
    public ResponseEntity<Object> smartDelete(
            Authentication authentication,
            @PathVariable Long id) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.getAccountById(id);

        account.setAccountActive(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("deleted account", HttpStatus.CREATED);
    }
}

