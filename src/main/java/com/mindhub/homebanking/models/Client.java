package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //Relacion uno a muchos con cuentas
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    //Relacion uno a muchos con prestamo de clientes
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientloans;

    //Relacion uno a muchos con tarjetas
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Client() { }

    //metodo constructor que me devolvera un cliente
    public Client(String firstName, String lastName, String email, String password) { //parametros cuando llame al constructor
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public long getId() { return id; }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return firstName + " " + lastName + " " + email;
    } //retorna un string

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccount() {
        return accounts;
    } //me retorna una lista de accounts
    public void addAccount(Account account) { //metodo Account agregar cuenta
        account.setClient(this);
        accounts.add(account);
    }

    public  Set<ClientLoan> getClientloans() { return clientloans; }

    public void setClientloan(Set<ClientLoan> clientloans) {
        this.clientloans = clientloans;
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientloans.add(clientLoan);
    }

    public Set<Card> getCard() {
        return cards;
    } //me retorna una lista de cards

    public void addCard(Card card) { //metodo Card agregar tarjeta
        card.setClient(this);
        cards.add(card);
    }
}



//mapppby se asocia con account.java en private Client client
//fetch=FetchType.EAGER tener actulizada la informacion y el EASY espera a que se le diga que haga tal cosa
//new HashSet<>() reserva un espacio en memoria para guardar el set accounts
//metodo setUsuario = setear cada propiedad cliente(el dueño sera el cliente que ejecute. this = es cada cliente que ejecuta el metodo Account, ace referencia en hamebankig.app al cliente que se crea la cuenta
//getter obtener las propiedades que estan encapsuladas
//setter modificar las propiedades que estan encapsuladas
//metodo addacount = para crear una cuenta (propiedad account de tipo Account)
//por cada cliente crea una cuenta y agrega a la lista accounts la cuenta account pasada por parametro

