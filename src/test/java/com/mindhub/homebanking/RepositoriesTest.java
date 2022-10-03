package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


    //@DataJpaTest // funciona solamente con postgress
    @SpringBootTest // funciona para h2
    @AutoConfigureTestDatabase(replace = NONE)
    public class RepositoriesTest {

        @Autowired
        LoanRepository loanRepository;

        @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }

        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }

        @Autowired
        ClientRepository clientRepository;

        @Test
        public void existClients(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients,is(not(empty())));
        }

        @Test
        public void existClient() {
            List<Client> clients = clientRepository.findAll();
            assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));
        }

        @Autowired
        AccountRepository accountRepository;

        @Test
        public void existAccounts(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts,is(not(empty())));
        }

        @Test
        public void existAccount() {
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasItem(hasProperty("numberAccount", is("VIN001"))));
        }

        @Autowired
        CardRepository cardRepository;

        @Test
        public void existCards(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards,is(not(empty())));
        }

        @Test
        public void existCard() {
            List<Card> cards = cardRepository.findAll();
            assertThat(cards, hasItem(hasProperty("cvv", is(242))));
        }

        @Autowired
        TransactionRepository transactionRepository;

        @Test
        public void existTransactions(){
            List<Transaction> transactions = transactionRepository.findAll();
            assertThat(transactions,is(not(empty())));
        }

        @Test
        public void existTransaction() {
            List<Transaction> transactions = transactionRepository.findAll();
            assertThat(transactions, hasItem(hasProperty("description", is("Transferencias a cuenta de terceros"))));
        }
    }
