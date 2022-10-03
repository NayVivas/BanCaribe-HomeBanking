package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import java.util.Set;
import java.util.stream.Collectors;

//DTO crear clases cuyo unico proposito es tener las propiedades que se desean enviar o recibir (mostrar los datos como yo quiero)
public class ClientDTO {
    
    private long id;
    private String firstName;
    private String lastName;
    private String emailClient;

    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO() {
    }
    
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.emailClient = client.getEmail();
        this.accounts = client.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans = client.getClientloans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        this.cards = client.getCard().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public Set<AccountDTO> getAccount() {
        return accounts;
    }
    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }
}
