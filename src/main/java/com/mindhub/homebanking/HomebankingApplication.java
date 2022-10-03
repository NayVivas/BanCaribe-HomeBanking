package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	} //primer metodo a ejecutar

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) { //le digo a la palicacion que lo primero que debe hacer es traerme los datos del repositorio
		return (args) -> {

			//clientes
			Client clientOne = new Client("Melba", "Lorenzo", "melbalorenzo@gmail.com", passwordEncoder.encode("12345678"));
			Client clientTwo = new Client("Jack", "Bauer", "jackb@gmail.com", passwordEncoder.encode("123456"));
			Client admin = new Client("admin", "admin", "naylu@admin.com", passwordEncoder.encode("252525"));

			//cuentas
			Account accountOne = new Account("VIN001", LocalDateTime.now(), 5000.00, clientOne, true, AccountType.AHORRO);
			Account accountTwo = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500.00, clientOne, true, AccountType.CORRIENTE);
			Account accountThree = new Account("VIN003", LocalDateTime.now(), 1500.00, clientTwo, true, AccountType.AHORRO);
			Account accountFour = new Account("VIN004", LocalDateTime.now().plusDays(3), 10500.00, clientTwo, true, AccountType.CORRIENTE);

			//transacciones
			Transaction transactionOne = new Transaction(TransactionType.DEBIT, LocalDateTime.now().plusDays(1), -2500.00, "Transferencias a cuenta de terceros", accountOne);
			Transaction transactionTwo = new Transaction(TransactionType.DEBIT, LocalDateTime.now().plusDays(2), -30000.00, "Transferencias a cuentas propias", accountOne);
			Transaction transactionThree = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), -50000.00, "Pago de servicios", accountTwo);
			Transaction transactionFour = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), -40000.00, "Pago Afip", accountTwo);
			Transaction transactionFive = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), 100000.00, "Acreditación de sueldo", accountOne);
			Transaction transactionSix = new Transaction(TransactionType.CREDIT, LocalDateTime.now().plusDays(3), 10000.00, "Acreditación de AUH", accountOne);
			Transaction transactionSeven = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), 5000.00, "Transferencia de terceros", accountTwo);
			Transaction transactionEigth = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), 40000.00, "Transferencia de terceros", accountTwo);

			Transaction transactionOne0 = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), -2500.00, "Transferencias a cuenta de terceros", accountThree);
			Transaction transactionTwo1 = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), -30000.00, "Transferencias a cuentas propias", accountThree);
			Transaction transactionThree2 = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), -50000.00, "Pago de servicios", accountFour);
			Transaction transactionFour3 = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), -40000.00, "Pago Afip", accountFour);
			Transaction transactionFive4 = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), 100000.00, "Acreditación de sueldo", accountThree);
			Transaction transactionSix5 = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), 10000.00, "Acreditación de AUH", accountThree);
			Transaction transactionSeven6 = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), 5000.00, "Transferencia de terceros", accountFour);
			Transaction transactionEigth7 = new Transaction(TransactionType.CREDIT, LocalDateTime.now(), 40000.00, "Transferencia de terceros", accountFour);

			//Tipos de prestamos
			List<Integer> paymentsOne = Arrays.asList (12, 24, 48, 60);
			List<Integer> paymentsTwo = Arrays.asList (6, 12 , 24);
			List<Integer> paymentsThree = Arrays.asList (6,12,24,36);

			Loan loanOne = new Loan("Hipotecario", 500000.00 , paymentsOne, 0.15);
			Loan loanTwo = new Loan("Personal", 100000.00 , paymentsTwo, 0.20);
			Loan loanThree = new Loan("Automotriz", 300000.00 , paymentsThree, 0.25);

			//prestamos para cada cliente
			ClientLoan clientLoanOne = new ClientLoan(400000.00, 60, clientOne, loanOne);
			ClientLoan clientLoanTwo = new ClientLoan(50000.00, 12, clientOne, loanTwo);

			ClientLoan clientLoanThree = new ClientLoan(100000.00, 24, clientTwo, loanTwo);
			ClientLoan clientLoanFour = new ClientLoan(200000.00, 36, clientTwo, loanThree);

			//Tarjetas

			Card cardGold = new Card(clientOne.getFirstName() + " " + clientOne.getLastName(), CardType.DEBIT, CardColor.GOLD, "0402" + " " + "0902" + " " +  "2205" + " " + "6067", 242, LocalDateTime.now(), LocalDateTime.of(2021,02,01, 10, 10, 10), clientOne, true);
			Card cardTitanium = new Card(clientOne.getFirstName() + " " + clientOne.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "4582" + " " + "2641" + " " +  "2424" + " " + "3652", 424, LocalDateTime.now(), LocalDateTime.now().plusYears(5), clientOne, true);
			Card cardGoldtwo = new Card(clientTwo.getFirstName() + " " + clientOne.getLastName(), CardType.DEBIT, CardColor.GOLD, "8574" + " " + "9634" + " " +  "3625" + " " + "4528", 155, LocalDateTime.now(), LocalDateTime.now().plusYears(5), clientTwo, true);
			Card cardSilver = new Card(clientTwo.getFirstName() + " " + clientOne.getLastName(), CardType.CREDIT, CardColor.SILVER, "4782" + " " + "0023" + " " +  "6895" + " " + "3525", 635, LocalDateTime.now(), LocalDateTime.now().plusYears(5),clientTwo, true);



			//Guardar en repositorio
			clientRepository.save(clientOne);
			clientRepository.save(clientTwo);
			clientRepository.save(admin);

			clientOne.addAccount(accountOne);
			accountRepository.save(accountOne);

			clientOne.addAccount(accountTwo);
			accountRepository.save(accountTwo);

			clientTwo.addAccount(accountThree);
			accountRepository.save(accountThree);

			clientTwo.addAccount(accountFour);
			accountRepository.save(accountFour);

			transactionRepository.save(transactionOne);
			transactionRepository.save(transactionTwo);
			transactionRepository.save(transactionThree);
			transactionRepository.save(transactionFour);
			transactionRepository.save(transactionFive);
			transactionRepository.save(transactionSix);
			transactionRepository.save(transactionSeven);
			transactionRepository.save(transactionEigth);

			transactionRepository.save(transactionOne0);
			transactionRepository.save(transactionTwo1);
			transactionRepository.save(transactionThree2);
			transactionRepository.save(transactionFour3);
			transactionRepository.save(transactionFive4);
			transactionRepository.save(transactionSix5);
			transactionRepository.save(transactionSeven6);
			transactionRepository.save(transactionEigth7);


			loanRepository.save(loanOne);
			loanRepository.save(loanTwo);
			loanRepository.save(loanThree);

			clientLoanRepository.save(clientLoanOne);
			clientLoanRepository.save(clientLoanTwo);
			clientLoanRepository.save(clientLoanThree);
			clientLoanRepository.save(clientLoanFour);

			clientOne.addCard(cardGold);
			cardRepository.save(cardGold);

			clientOne.addCard(cardTitanium);
			cardRepository.save(cardTitanium);

			clientTwo.addCard(cardGoldtwo);
			cardRepository.save(cardGoldtwo);

			clientTwo.addCard(cardSilver);
			cardRepository.save(cardSilver);

		};
	}
}

// CommandLineRunner: le dice a la aplicacion que esas son las lineas de codigo que se ejecute primero
//el cliente vive en la memoria de la aplicacion para que viva en la base de datos se crea el repositorio
//Sobrecargas de metodos: es cuando se tiene varios constructores con diferentes parametros, son dos constructores
//pero es el mismo ya que tiene el mismo nombre y reacciona diferente depende de esos parametros